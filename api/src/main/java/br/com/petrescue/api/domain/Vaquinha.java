package br.com.petrescue.api.domain;

import br.com.petrescue.api.controller.dto.VaquinhaDTO;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "vaquinha")
public class Vaquinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition="DECIMAL(8,2)")
    private Double meta;

    @Column(name = "valor_arrecadado", columnDefinition="DECIMAL(8,2) UNSIGNED", nullable = false)
    private Double valorArrecadado;

    @Column
    private String foto;

    @Column(nullable = false)
    private LocalDate inicio;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    public Vaquinha(VaquinhaDTO vaquinhaDTO) {
        this.id = vaquinhaDTO.getId();
        this.meta = vaquinhaDTO.getMeta();
        this.valorArrecadado = vaquinhaDTO.getValorArrecadado();
        this.foto = vaquinhaDTO.getFoto();
        this.inicio = vaquinhaDTO.getInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.titulo = vaquinhaDTO.getTitulo();
        this.descricao = vaquinhaDTO.getDescricao();
        this.ativo = vaquinhaDTO.getAtivo();
    }
}
