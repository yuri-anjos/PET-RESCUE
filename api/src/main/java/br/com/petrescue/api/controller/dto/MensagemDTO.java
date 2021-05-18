package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Mensagem;
import java.time.ZoneId;
import java.util.Date;
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
    private Date horario;
    private String texto;
    private Integer idAutor;
    private String nomeAutor;
    private Integer idConversa;

    public MensagemDTO(Mensagem mensagem) {
        this.id = mensagem.getId();
        this.horario = Date.from(mensagem.getHorario().atZone(ZoneId.systemDefault()).toInstant());
        this.texto = mensagem.getTexto();
        this.idAutor = mensagem.getAutor().getId();
        this.nomeAutor = mensagem.getAutor().getNome();
        this.idConversa = mensagem.getConversa().getId();
    }
}
