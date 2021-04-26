package com.example.petrescue.domain;

import java.time.LocalDateTime;

public class Mensagem {

    private Integer id;
    private LocalDateTime horario;
    private String texto;
    private Integer autor;
    private Integer conversa;

    public Mensagem(Integer id, LocalDateTime horario, String texto, Integer autor, Integer conversa) {
        this.id = id;
        this.horario = horario;
        this.texto = texto;
        this.autor = autor;
        this.conversa = conversa;
    }

    public Mensagem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getAutor() {
        return autor;
    }

    public void setAutor(Integer autor) {
        this.autor = autor;
    }

    public Integer getConversa() {
        return conversa;
    }

    public void setConversa(Integer conversa) {
        this.conversa = conversa;
    }
}
