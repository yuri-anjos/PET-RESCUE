package com.example.petrescue.domain;

import com.example.petrescue.domain.enums.TipoAnimal;
import com.example.petrescue.domain.enums.TipoPIN;
import com.example.petrescue.domain.subClasses.Localizacao;

import java.io.Serializable;
import java.util.Date;

public class AnimalPIN implements Serializable {

    private Integer id;
    private String foto;
    private String descricao;
    private TipoAnimal tipoAnimal;
    private String raca;
    private TipoPIN tipoPIN;
    private Boolean ativo;
    private Localizacao localizacao;
    private Date dataCadastro;
    private Integer idUsuario;
    private String nomeUsuario;

    public AnimalPIN(Integer id, String foto, String descricao, TipoAnimal tipoAnimal, String raca, TipoPIN tipoPIN, Boolean ativo, Localizacao localizacao, Date dataCadastro, Integer idUsuario, String nomeUsuario) {
        this.id = id;
        this.foto = foto;
        this.descricao = descricao;
        this.tipoAnimal = tipoAnimal;
        this.raca = raca;
        this.tipoPIN = tipoPIN;
        this.ativo = ativo;
        this.localizacao = localizacao;
        this.dataCadastro = dataCadastro;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public AnimalPIN() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoAnimal getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(TipoAnimal tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public TipoPIN getTipoPIN() {
        return tipoPIN;
    }

    public void setTipoPIN(TipoPIN tipoPIN) {
        this.tipoPIN = tipoPIN;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
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
}
