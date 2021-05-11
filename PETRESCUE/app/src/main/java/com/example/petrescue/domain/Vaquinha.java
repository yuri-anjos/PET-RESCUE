package com.example.petrescue.domain;

import java.io.Serializable;
import java.util.Date;

public class Vaquinha implements Serializable {

    private Integer id;
    private Double meta;
    private Double valorArrecadado;
    private String foto;
    private Date inicio;
    private String titulo;
    private String descricao;
    private Boolean ativo;
    private Integer idUsuario;
    private String nomeUsuario;

    public Vaquinha(Integer id, Double meta, Double valorArrecadado, String foto, Date inicio, String titulo, String descricao, Boolean ativo, Integer idUsuario, String nomeUsuario) {
        this.id = id;
        this.meta = meta;
        this.valorArrecadado = valorArrecadado;
        this.foto = foto;
        this.inicio = inicio;
        this.titulo = titulo;
        this.descricao = descricao;
        this.ativo = ativo;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public Vaquinha() {
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

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    @Override
    public String toString() {
        return "Vaquinha{" +
                "id=" + id +
                ", meta=" + meta +
                ", valorArrecadado=" + valorArrecadado +
                ", foto='" + foto + '\'' +
                ", inicio=" + inicio +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ativo=" + ativo +
                ", idUsuario=" + idUsuario +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                '}';
    }
}
