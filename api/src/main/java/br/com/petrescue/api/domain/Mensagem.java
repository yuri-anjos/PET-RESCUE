package br.com.petrescue.api.domain;

import br.com.petrescue.api.controller.dto.MensagemDTO;
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
@Table(name = "mensagem")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime horario;

    @Column(nullable = false)
    private String texto;

    @JoinColumn(name = "id_autor")
    @ManyToOne
    private Usuario autor;

    @JoinColumn(name = "id_conversa")
    @ManyToOne
    private Conversa conversa;

    public Mensagem(MensagemDTO mensagemDTO) {
        this.id = mensagemDTO.getId();
        this.horario = mensagemDTO.getHorario() == null ? null : mensagemDTO.getHorario().toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
        this.texto = mensagemDTO.getTexto();
    }
}
