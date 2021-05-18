package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Conversa;
import br.com.petrescue.api.domain.Mensagem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversaDTO {

    private Integer id;
    private Integer idUsuarioUm;
    private Integer idUsuarioDois;
    private String nomeUsuarioUm;
    private String nomeUsuarioDois;
    private MensagemDTO ultimaMensagem;

    public ConversaDTO(Conversa conversa) {
        this.id = conversa.getId();
        this.idUsuarioUm = conversa.getUsuarioUm().getId();
        this.idUsuarioDois = conversa.getUsuarioDois().getId();
        this.nomeUsuarioUm = conversa.getUsuarioUm().getNome();
        this.nomeUsuarioDois = conversa.getUsuarioDois().getNome();
    }

    public ConversaDTO(Conversa conversa, Mensagem mensagem) {
        this.id = conversa.getId();
        this.idUsuarioUm = conversa.getUsuarioUm().getId();
        this.idUsuarioDois = conversa.getUsuarioDois().getId();
        this.nomeUsuarioUm = conversa.getUsuarioUm().getNome();
        this.nomeUsuarioDois = conversa.getUsuarioDois().getNome();
        this.ultimaMensagem = mensagem == null ? null : new MensagemDTO(mensagem);
    }
}
