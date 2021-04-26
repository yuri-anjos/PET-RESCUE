package com.example.petrescue.domain;

public class Conversa {

    private Integer id;
    private Integer usuarioUm;
    private Integer usuarioDois;

    public Conversa(Integer id, Integer usuarioUm, Integer usuarioDois) {
        this.id = id;
        this.usuarioUm = usuarioUm;
        this.usuarioDois = usuarioDois;
    }

    public Conversa() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioUm() {
        return usuarioUm;
    }

    public void setUsuarioUm(Integer usuarioUm) {
        this.usuarioUm = usuarioUm;
    }

    public Integer getUsuarioDois() {
        return usuarioDois;
    }

    public void setUsuarioDois(Integer usuarioDois) {
        this.usuarioDois = usuarioDois;
    }
}
