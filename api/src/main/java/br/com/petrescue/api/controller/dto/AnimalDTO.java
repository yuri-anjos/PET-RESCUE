package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Animal;
import br.com.petrescue.api.domain.enums.Sexo;
import br.com.petrescue.api.domain.enums.SituacaoAdocao;
import br.com.petrescue.api.domain.enums.TipoAnimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {

    private Integer id;
    private SituacaoAdocao situacaoAdocao;
    private String foto;
    private TipoAnimal tipoAnimal;
    private String raca;
    private Sexo sexo;
    private Integer dataNascimento;
    private String descricao;
    private String vacinas;
    private Integer usuario;

    public AnimalDTO(Animal animal) {
        this.id = animal.getId();
        this.situacaoAdocao = animal.getSituacaoAdocao();
        this.foto = animal.getFoto();
        this.tipoAnimal = animal.getTipoAnimal();
        this.raca = animal.getRaca();
        this.sexo = animal.getSexo();
        this.dataNascimento = animal.getDataNascimento();
        this.descricao = animal.getDescricao();
        this.vacinas = animal.getVacinas();
        this.usuario = animal.getUsuario().getId();
    }
}
