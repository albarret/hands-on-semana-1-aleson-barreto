package com.startup;

import com.startup.servico.ProcessadorRelatorio;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String continua = "S";
        while(continua.equalsIgnoreCase("S")) {
            try {
                LOGGER.log(Level.INFO, "Insira o ano de refência para a consulta");
                ProcessadorRelatorio.incio(scanner.nextInt());
                LOGGER.info("Relatorio gerado com sucesso. Gerar para outro ano[S/N]?");
                continua = scanner.next();
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }
        }


    }
}