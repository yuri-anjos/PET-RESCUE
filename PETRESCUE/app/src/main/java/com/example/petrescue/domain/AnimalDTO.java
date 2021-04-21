package com.example.petrescue.domain;

import com.example.petrescue.domain.enums.Sexo;
import com.example.petrescue.domain.enums.SituacaoAdocao;
import com.example.petrescue.domain.enums.TipoAnimal;

import java.time.LocalDate;

public class AnimalDTO {

    private Integer id;
    private SituacaoAdocao situacaoAdocao;
    private String foto;
    private TipoAnimal tipoAnimal;
    private String raca;
    private Sexo sexo;
    private LocalDate dataNascimento;
    private String descricao;
    private String vacinas;
    private Integer usuario;

    public AnimalDTO(Integer id, SituacaoAdocao situacaoAdocao, String foto, TipoAnimal tipoAnimal, String raca, Sexo sexo, LocalDate dataNascimento, String descricao, String vacinas, Integer usuario) {
        this.id = id;
        this.situacaoAdocao = situacaoAdocao;
        this.foto = foto;
        this.tipoAnimal = tipoAnimal;
        this.raca = raca;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.descricao = descricao;
        this.vacinas = vacinas;
        this.usuario = usuario;
    }

    public AnimalDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
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

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }
}
