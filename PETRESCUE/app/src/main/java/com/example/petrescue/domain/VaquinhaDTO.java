package com.example.petrescue.domain;

import java.time.LocalDate;

public class VaquinhaDTO {

    private Integer id;
    private Double meta;
    private Double valorArrecadado;
    private String foto;
    private LocalDate inicio;
    private String titulo;
    private String descricao;
    private Boolean ativo;
    private Integer usuario;

    public VaquinhaDTO(Integer id, Double meta, Double valorArrecadado, String foto, LocalDate inicio, String titulo, String descricao, Boolean ativo, Integer usuario) {
        this.id = id;
        this.meta = meta;
        this.valorArrecadado = valorArrecadado;
        this.foto = foto;
        this.inicio = inicio;
        this.titulo = titulo;
        this.descricao = descricao;
        this.ativo = ativo;
        this.usuario = usuario;
    }

    public VaquinhaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMeta() {
        return meta;
    }

    public void setMeta(Double meta) {
        this.meta = meta;
    }

    public Double getValorArrecadado() {
        return valorArrecadado;
    }

    public void setValorArrecadado(Double valorArrecadado) {
        this.valorArrecadado = valorArrecadado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }
}
