package br.com.petrescue.api.domain;

import br.com.petrescue.api.controller.dto.AnimalPINDTO;
import br.com.petrescue.api.domain.enums.TipoAnimal;
import br.com.petrescue.api.domain.enums.TipoPIN;
import br.com.petrescue.api.domain.subClasses.Localizacao;
import java.time.LocalDate;
import java.time.ZoneId;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private Boolean ativo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "latitude", column = @Column(name = "latitude", nullable = false)),
            @AttributeOverride( name = "longitude", column = @Column(name = "longitude", nullable = false))
    })
    private Localizacao localizacao;

    @Column(nullable = false, name = "data_cadastro")
    private LocalDate dataCadastro;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    public AnimalPIN(AnimalPINDTO animalPINDTO) {
        this.id = animalPINDTO.getId();
        this.foto = animalPINDTO.getFoto();
        this.descricao = animalPINDTO.getDescricao();
        this.tipoAnimal = animalPINDTO.getTipoAnimal();
        this.raca = animalPINDTO.getRaca();
        this.tipoPIN = animalPINDTO.getTipoPIN();
        this.ativo = animalPINDTO.getAtivo();
        this.localizacao = animalPINDTO.getLocalizacao();
        this.dataCadastro = animalPINDTO.getDataCadastro() == null ? null : animalPINDTO.getDataCadastro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
