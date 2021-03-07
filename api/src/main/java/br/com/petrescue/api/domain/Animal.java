package br.com.petrescue.api.domain;

import br.com.petrescue.api.domain.enums.Sexo;
import br.com.petrescue.api.domain.enums.TipoAnimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Usuario usuario;

    private String foto;

    private TipoAnimal tipoAnimal;

    private String raca;

    private Sexo sexo;

    private LocalDate dataNascimento;

    private String descricao;

    private String vacinas;
}
