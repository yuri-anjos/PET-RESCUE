package com.example.petrescue.domain;

import com.example.petrescue.domain.enums.Sexo;
import com.example.petrescue.domain.enums.SituacaoAdocao;
import com.example.petrescue.domain.enums.TipoAnimal;

import java.io.Serializable;

public class Animal implements Serializable {

    private Integer id;
    private String nome;
    private SituacaoAdocao situacaoAdocao;
    private String foto;
    private TipoAnimal tipoAnimal;
    private String raca;
    private Sexo sexo;
    private Integer dataNascimento;
    private String descricao;
    private String vacinas;
    private Integer idUsuario;
    private String nomeUsuario;

    public Animal() {
    }

    public Animal(Integer id, String nome, SituacaoAdocao situacaoAdocao, String foto, TipoAnimal tipoAnimal, String raca, Sexo sexo, Integer dataNascimento, String descricao, String vacinas, Integer idUsuario, String nomeUsuario) {
        this.id = id;
        this.nome = nome;
        this.situacaoAdocao = situacaoAdocao;
        this.foto = foto;
        this.tipoAnimal = tipoAnimal;
        this.raca = raca;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.descricao = descricao;
        this.vacinas = vacinas;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SituacaoAdocao getSituacaoAdocao() {
        return situacaoAdocao;
    }

    public void setSituacaoAdocao(SituacaoAdocao situacaoAdocao) {
        this.situacaoAdocao = situacaoAdocao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Integer getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Integer dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVacinas() {
        return vacinas;
    }

    public void setVacinas(String vacinas) {
        this.vacinas = vacinas;
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
        return "Animal{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", situacaoAdocao=" + situacaoAdocao +
                ", foto='" + foto + '\'' +
                ", tipoAnimal=" + tipoAnimal +
                ", raca='" + raca + '\'' +
                ", sexo=" + sexo +
                ", dataNascimento=" + dataNascimento +
                ", descricao='" + descricao + '\'' +
                ", vacinas='" + vacinas + '\'' +
                ", idUsuario=" + idUsuario +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                '}';
    }
}
