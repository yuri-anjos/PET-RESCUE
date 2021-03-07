package br.com.petrescue.api.domain;

import br.com.petrescue.api.domain.enums.Sexo;
import br.com.petrescue.api.domain.enums.TipoAnimal;
import br.com.petrescue.api.domain.enums.TipoPIN;
import br.com.petrescue.api.domain.subClasses.Localizacao;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name = "animal_pin")
public class AnimalPIN {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String foto;

    private String descricao;

    private TipoAnimal tipoAnimal;

    private String raca;

    private TipoPIN tipoPIN;

    private Usuario usuario;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "latitude", column = @Column(name = "latitude")),
            @AttributeOverride( name = "longitude", column = @Column(name = "longitude"))
    })
    private Localizacao localizacao;
}
