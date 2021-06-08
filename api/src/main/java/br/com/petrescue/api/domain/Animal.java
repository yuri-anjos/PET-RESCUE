package br.com.petrescue.api.domain;

import br.com.petrescue.api.controller.dto.AnimalDTO;
import br.com.petrescue.api.domain.enums.Sexo;
import br.com.petrescue.api.domain.enums.SituacaoAdocao;
import br.com.petrescue.api.domain.enums.TipoAnimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "situacao_adocao", nullable = false)
    @Enumerated(EnumType.STRING)
    private SituacaoAdocao situacaoAdocao;

    @Column
    private String foto;

    @Column(name = "tipo_animal", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAnimal tipoAnimal;

    @Column(nullable = false)
    private Boolean castrado;

    @Column(nullable = false)
    private String raca;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column( name = "data_nascimento")
    private Integer dataNascimento;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String vacinas;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    public Animal(AnimalDTO animalDTO) {
        this.id = animalDTO.getId();
        this.nome = animalDTO.getNome();
        this.situacaoAdocao = animalDTO.getSituacaoAdocao();
        this.foto = animalDTO.getFoto();
        this.tipoAnimal = animalDTO.getTipoAnimal();
        this.castrado = animalDTO.getCastrado();
        this.raca = animalDTO.getRaca();
        this.sexo = animalDTO.getSexo();
        this.dataNascimento = animalDTO.getDataNascimento();
        this.descricao = animalDTO.getDescricao();
        this.vacinas = animalDTO.getVacinas();
    }
}
