package com.example.petrescue.domain;

import com.example.petrescue.domain.enums.TipoUsuario;
import com.example.petrescue.domain.subClasses.Localizacao;

public class UsuarioDTO {

    private Integer id;
    private Double saldo;
    private String email;
    private String nome;
    private String foto;
    private Localizacao localizacao;

    private TipoUsuario tipoUsuario;

    private String nomeOng;
    private String cpfCnpj;
    private String descricao;

    public UsuarioDTO(Integer id, Double saldo, String email, String nome, String foto, Localizacao localizacao, TipoUsuario tipoUsuario, String nomeOng, String cpfCnpj, String descricao) {
        this.id = id;
        this.saldo = saldo;
        this.email = email;
        this.nome = nome;
        this.foto = foto;
        this.localizacao = localizacao;
        this.tipoUsuario = tipoUsuario;
        this.nomeOng = nomeOng;
        this.cpfCnpj = cpfCnpj;
        this.descricao = descricao;
    }

    public UsuarioDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNomeOng() {
        return nomeOng;
    }

    public void setNomeOng(String nomeOng) {
        this.nomeOng = nomeOng;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
