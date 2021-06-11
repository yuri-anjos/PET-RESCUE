package com.example.petrescue.domain;

import android.os.CpuUsageInfo;

import com.example.petrescue.domain.enums.TipoUsuario;
import com.example.petrescue.domain.subClasses.Localizacao;

import java.io.Serializable;

public class Usuario implements Serializable {

    private Integer id;
    private Double saldo;
    private String email;
    private String senha;
    private String nome;
    private String foto;

    private Localizacao localizacao;

    private TipoUsuario tipoUsuario;

    private String cpfCnpj;
    private String descricao;

    public Usuario(Integer id, Double saldo, String email, String senha, String nome, String foto, TipoUsuario tipoUsuario, String cpfCnpj, String descricao) {
        this.id = id;
        this.saldo = saldo;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.foto = foto;
        this.tipoUsuario = tipoUsuario;
        this.cpfCnpj = cpfCnpj;
        this.descricao = descricao;
    }

    public Usuario() {
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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

    public Usuario getCopy() {
        Usuario u = new Usuario();
        u.setId(this.id);
        u.setSaldo(this.saldo);
        u.setEmail(this.email);
        u.setSenha(this.senha);
        u.setNome(this.nome);
        u.setFoto(this.foto);
        u.setTipoUsuario(this.tipoUsuario);
        u.setCpfCnpj(this.cpfCnpj);
        u.setDescricao(this.descricao);
        return u;
    }
}
