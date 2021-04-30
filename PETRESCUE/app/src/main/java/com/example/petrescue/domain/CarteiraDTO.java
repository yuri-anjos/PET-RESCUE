package com.example.petrescue.domain;

public class CarteiraDTO {
    private Integer usuario;
    private Double saldoAdicional;

    public CarteiraDTO(Integer usuario, Double saldoAdicional) {
        this.usuario = usuario;
        this.saldoAdicional = saldoAdicional;
    }

    public CarteiraDTO() {
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Double getSaldoAdicional() {
        return saldoAdicional;
    }

    public void setSaldoAdicional(Double saldoAdicional) {
        this.saldoAdicional = saldoAdicional;
    }
}
