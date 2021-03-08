package br.com.petrescue.api.domain;

import br.com.petrescue.api.domain.enums.TipoAnimal;
import br.com.petrescue.api.domain.enums.TipoPIN;
import br.com.petrescue.api.domain.subClasses.Localizacao;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @Column
    private String foto;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "tipo_animal", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAnimal tipoAnimal;

    @Column
    private String raca;

    @Column(name = "tipo_pin", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPIN tipoPIN;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "latitude", column = @Column(name = "latitude", nullable = false)),
            @AttributeOverride( name = "longitude", column = @Column(name = "longitude", nullable = false))
    })
    private Localizacao localizacao;

    @Column
    private boolean ativo;
}
