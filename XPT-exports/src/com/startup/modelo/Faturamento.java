package com.startup.modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Faturamento {

    private String company;
    private Integer mes;
    private Integer ano;
    private Date dataParcela1;
    private BigDecimal parcela1;
    private Date dataParcela2;
    private BigDecimal parcela2;
    private Date dataParcela3;
    private BigDecimal parcela3;

    public Faturamento(String company, Integer mes, Integer ano) {
        this.company = company;
        this.mes = mes;
        this.ano = ano;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Date getDataParcela1() {
        return dataParcela1;
    }

    public void setDataParcela1(Date dataParcela1) {
        this.dataParcela1 = dataParcela1;
    }

    public BigDecimal getParcela1() {
        return parcela1;
    }

    public void setParcela1(BigDecimal parcela1) {
        this.parcela1 = parcela1;
    }

    public Date getDataParcela2() {
        return dataParcela2;
    }

    public void setDataParcela2(Date dataParcela2) {
        this.dataParcela2 = dataParcela2;
    }

    public BigDecimal getParcela2() {
        return parcela2;
    }

    public void setParcela2(BigDecimal parcela2) {
        this.parcela2 = parcela2;
    }

    public Date getDataParcela3() {
        return dataParcela3;
    }

    public void setDataParcela3(Date dataParcela3) {
        this.dataParcela3 = dataParcela3;
    }

    public BigDecimal getParcela3() {
        return parcela3;
    }

    public void setParcela3(BigDecimal parcela3) {
        this.parcela3 = parcela3;
    }

    public String toString() {
        return "Faturamento:"
                + "\n Company: " + getCompany()
                + "\nMes: " + getMes().toString()
                + "\nAno: " + getAno().toString()
                + "\nData Parcela 1: " + getDataParcela1().toString()
                + "\nParcela 1: " + getParcela1().toString()
                + "\nData Parcela 2: " + getDataParcela2().toString()
                + "\nParcela 2: " + getParcela2().toString()
                + "\nData Parcela 3: " + getDataParcela3().toString()
                + "\nParcela 3: " + getParcela3().toString() + "\n";
    }

    public BigDecimal somaParcelas() {
        return getParcela1().add(getParcela2()).add(getParcela3());
    }
}
