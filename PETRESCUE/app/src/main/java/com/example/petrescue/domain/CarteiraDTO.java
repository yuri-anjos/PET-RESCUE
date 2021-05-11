package com.example.petrescue.domain;

public class CarteiraDTO {
    private Integer idUsuario;
    private Double saldoAdicional;

    public CarteiraDTO(Integer idUsuario, Double saldoAdicional) {
        this.idUsuario = idUsuario;
        this.saldoAdicional = saldoAdicional;
    }

    public CarteiraDTO() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getSaldoAdicional() {
        return saldoAdicional;
    }

    public void setSaldoAdicional(Double saldoAdicional) {
        this.saldoAdicional = saldoAdicional;
    }
}
