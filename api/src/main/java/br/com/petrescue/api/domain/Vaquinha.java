package br.com.petrescue.api.domain;

import java.time.LocalDate;
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

    @Column(columnDefinition="DECIMAL(8,2) UNSIGNED")
    private Double meta;

    @Column(name = "valor_arrecadado", columnDefinition="DECIMAL(8,2) UNSIGNED")
    private Double valorArrecadado;

    @Column(nullable = false)
    private LocalDate inicio;

    @Column(nullable = false)
    private String descricao;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;
}
