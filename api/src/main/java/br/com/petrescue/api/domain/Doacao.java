package br.com.petrescue.api.domain;

import br.com.petrescue.api.controller.dto.DoacaoDTO;
import java.time.LocalDateTime;
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
@Table(name = "doacao")
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition="DECIMAL(7,2) UNSIGNED")
    private Double quantia;

    @Column(nullable = false)
    private LocalDateTime quando;

    @JoinColumn(name = "id_doador")
    @ManyToOne
    private Usuario doador;

    @JoinColumn(name = "id_vaquinha")
    @ManyToOne
    private Vaquinha vaquinha;

    public Doacao(DoacaoDTO doacaoDTO) {
        this.id = doacaoDTO.getId();
        this.quantia = doacaoDTO.getQuantia();
        this.quando = doacaoDTO.getQuando() == null ? null : doacaoDTO.getQuando().toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
    }
}
