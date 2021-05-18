package com.example.petrescue.domain;

import java.util.Date;

public class Mensagem {

    private Integer id;
    private Date horario;
    private String texto;
    private Integer idAutor;
    private String nomeAutor;
    private Integer idConversa;

    public Mensagem(Integer id, Date horario, String texto, Integer idAutor, String nomeAutor, Integer idConversa) {
        this.id = id;
        this.horario = horario;
        this.texto = texto;
        this.idAutor = idAutor;
        this.nomeAutor = nomeAutor;
        this.idConversa = idConversa;
    }

    public Mensagem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Integer getIdConversa() {
        return idConversa;
    }

    public void setIdConversa(Integer idConversa) {
        this.idConversa = idConversa;
    }
}
