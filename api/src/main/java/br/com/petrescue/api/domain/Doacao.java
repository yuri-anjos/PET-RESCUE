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
@Table(name = "doacao")
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition="DECIMAL(7,2) UNSIGNED")
    private Double quantia;

    @Column(nullable = false)
    private LocalDate quando;

    @JoinColumn(name = "id_doador")
    @ManyToOne
    private Usuario doador;

    @JoinColumn(name = "id_vaquinha")
    @ManyToOne
    private Vaquinha vaquinha;
}
