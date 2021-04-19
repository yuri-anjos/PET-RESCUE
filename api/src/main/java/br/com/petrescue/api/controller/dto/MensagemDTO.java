package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Mensagem;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensagemDTO {

    private Integer id;
    private LocalDateTime horario;
    private String texto;
    private Integer autor;
    private Integer conversa;

    public MensagemDTO(Mensagem mensagem) {
        this.id = mensagem.getId();
        this.horario = mensagem.getHorario();
        this.texto = mensagem.getTexto();
        this.autor = mensagem.getAutor().getId();
        this.conversa = mensagem.getConversa().getId();
    }
}
