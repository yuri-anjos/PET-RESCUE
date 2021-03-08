package br.com.petrescue.api.domain;

import br.com.petrescue.api.domain.enums.Sexo;
import br.com.petrescue.api.domain.enums.SituacaoAdocao;
import br.com.petrescue.api.domain.enums.TipoAnimal;
import java.time.LocalDate;
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

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    @Column
    private String foto;

    @Column(name = "tipo_animal", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAnimal tipoAnimal;

    @Column(name = "situacao_adocao", nullable = false)
    @Enumerated(EnumType.STRING)
    private SituacaoAdocao situacaoAdocao;

    @Column
    private String raca;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column( name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String vacinas;
}
