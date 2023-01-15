package com.startup.servico;

import com.startup.modelo.Faturamento;
import com.startup.modelo.Notas;

import java.io.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ProcessadorRelatorio {
    private static final String CAMINHO = "..\\hands-on-semana-1-aleson-barreto\\XPT-exports\\resources\\";
    private static final Logger LOGGER = Logger.getLogger(ProcessadorRelatorio.class.getName());
    private static final Locale locale = Locale.forLanguageTag("pt-BR");
    private static final Integer NOTAS_QTD_CAMPOS = 6;
    private static final Integer FATURAMENTOS_QTD_CAMPOS = 9;

    private ProcessadorRelatorio() {}

    public static void incio(Integer anoReferencia) throws IOException {

        var faturamentos = parseFaturamento(anoReferencia);
        var notas = parseNota(anoReferencia);
        var empresas = faturamentos.keySet();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO + "relatorio.txt"))) {
            for(String key : empresas) {
                BigDecimal acumuladorFaturamentos = BigDecimal.ZERO;
                var listaFaturamentos = faturamentos.get(key);
                BigDecimal acumuladorNotas = BigDecimal.ZERO;
                var listaNotas = notas.get(key);

                if(listaFaturamentos != null && !listaFaturamentos.isEmpty() && listaNotas != null && !listaNotas.isEmpty()) {
                    for(Faturamento faturmento : listaFaturamentos) {
                        acumuladorFaturamentos = acumuladorFaturamentos.add(faturmento.somaParcelas());
                    }
                    for(Notas nota : listaNotas) {
                        acumuladorNotas = acumuladorNotas.add(nota.getValor());
                    }
                }

                if(acumuladorNotas.equals(acumuladorFaturamentos)) {
                    writer.write("Empresa " + key + " EM CONFORMIDADE");
                } else {
                    writer.write("Empresa " + key + " EM NÃO CONFORMIDADE");
                }
                writer.newLine();
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public static Map<String, List<Faturamento>> parseFaturamento(Integer ano) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        var faturamentos = new ArrayList<Faturamento>();
        try(BufferedReader reader = new BufferedReader(new FileReader(CAMINHO + "faturamento.txt"))) {

            var lines = reader.lines().filter(l -> !l.isEmpty()).toList();
            lines = lines.subList(1, lines.size());
            var values = new String[FATURAMENTOS_QTD_CAMPOS];

            for(String line : lines) {
                values = line.split(";");

                if(Integer.parseInt(values[2]) == ano){
                    var faturamento = new Faturamento(values[0],
                            Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                    faturamento.setDataParcela1(dateFormat.parse(values[3]));
                    faturamento.setParcela1(BigDecimal.valueOf(parseNumber(values[4])));
                    faturamento.setDataParcela2(dateFormat.parse(values[5]));
                    faturamento.setParcela2(BigDecimal.valueOf(parseNumber(values[6])));
                    faturamento.setDataParcela3(dateFormat.parse(values[7]));
                    faturamento.setParcela3(BigDecimal.valueOf(parseNumber(values[8])));
                    faturamentos.add(faturamento);
                }

            }

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

        return faturamentos.stream().collect(Collectors.groupingBy(Faturamento::getCompany));
    }
    public static Map<String, List<Notas>> parseNota(Integer ano) {

        var notas = new ArrayList<Notas>();
        try(BufferedReader reader = new BufferedReader(new FileReader(CAMINHO + "nota.txt"))) {

            var lines = reader.lines().filter(l -> !l.isEmpty()).toList();
            lines = lines.subList(1, lines.size());
            var values = new String[NOTAS_QTD_CAMPOS];

            for(String line : lines) {
                values = line.split(";");

                if(Integer.parseInt(values[2]) == ano) {
                    notas.add(preencherNotas(values));
                }
            }

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

        return notas.stream().collect(Collectors.groupingBy(Notas::getCompany));
    }

    private static Double parseNumber(String number) throws ParseException {
        if(number == null) {
            return Double.valueOf("0");
        }
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);

        return numberFormatter.parse(number).doubleValue();

    }

    private static Notas preencherNotas(String[] values) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        var nota = new Notas();
        nota.setCompany(values[0]);
        nota.setMes(Integer.parseInt(values[1]));
        nota.setAno(Integer.parseInt(values[2]));

        try {
            nota.setValor(BigDecimal.valueOf(parseNumber(values[3])));
            nota.setDataEmissao(dateFormat.parse(values[4]));
            nota.setIdNota(Long.valueOf(values[5]));
            return nota;
        } catch (Exception e) {
            LOGGER.info("Registro invalido foi encontrado e seu valor será contabilizado como zero.");
            nota.setValor(BigDecimal.ZERO);
            nota.setDataEmissao(null);
            nota.setIdNota(null);
            return nota;
        }
    }
}
