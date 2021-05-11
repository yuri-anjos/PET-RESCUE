package com.example.petrescue.domain;

import java.util.Date;

public class Doacao {

    private Integer id;
    private Double quantia;
    private Date quando;
    private Integer idDoador;
    private Integer idVaquinha;

    public Doacao(Integer id, Double quantia, Date quando, Integer idDoador, Integer idVaquinha) {
        this.id = id;
        this.quantia = quantia;
        this.quando = quando;
        this.idDoador = idDoador;
        this.idVaquinha = idVaquinha;
    }

    public Doacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getQuantia() {
        return quantia;
    }

    public void setQuantia(Double quantia) {
        this.quantia = quantia;
    }

    public Date getQuando() {
        return quando;
    }

    public void setQuando(Date quando) {
        this.quando = quando;
    }

    public Integer getIdDoador() {
        return idDoador;
    }

    public void setIdDoador(Integer idDoador) {
        this.idDoador = idDoador;
    }

    public Integer getIdVaquinha() {
        return idVaquinha;
    }

    public void setIdVaquinha(Integer idVaquinha) {
        this.idVaquinha = idVaquinha;
    }
}
