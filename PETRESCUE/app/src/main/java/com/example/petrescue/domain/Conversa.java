package com.example.petrescue.domain;

public class Conversa {

    private Integer id;
    private Integer idUsuarioUm;
    private Integer idUsuarioDois;
    private String nomeUsuarioUm;
    private String nomeUsuarioDois;
    private Mensagem ultimaMensagem;
    private String foto;

    public Conversa(Integer id, Integer idUsuarioUm, Integer idUsuarioDois, String nomeUsuarioUm, String nomeUsuarioDois, Mensagem ultimaMensagem, String foto) {
        this.id = id;
        this.idUsuarioUm = idUsuarioUm;
        this.idUsuarioDois = idUsuarioDois;
        this.nomeUsuarioUm = nomeUsuarioUm;
        this.nomeUsuarioDois = nomeUsuarioDois;
        this.ultimaMensagem = ultimaMensagem;
        this.foto = foto;
    }

    public Conversa() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuarioUm() {
        return idUsuarioUm;
    }

    public void setIdUsuarioUm(Integer idUsuarioUm) {
        this.idUsuarioUm = idUsuarioUm;
    }

    public Integer getIdUsuarioDois() {
        return idUsuarioDois;
    }

    public void setIdUsuarioDois(Integer idUsuarioDois) {
        this.idUsuarioDois = idUsuarioDois;
    }

    public String getNomeUsuarioUm() {
        return nomeUsuarioUm;
    }

    public void setNomeUsuarioUm(String nomeUsuarioUm) {
        this.nomeUsuarioUm = nomeUsuarioUm;
    }

    public String getNomeUsuarioDois() {
        return nomeUsuarioDois;
    }

    public void setNomeUsuarioDois(String nomeUsuarioDois) {
        this.nomeUsuarioDois = nomeUsuarioDois;
    }

    public Mensagem getUltimaMensagem() {
        return ultimaMensagem;
    }

    public void setUltimaMensagem(Mensagem ultimaMensagem) {
        this.ultimaMensagem = ultimaMensagem;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
