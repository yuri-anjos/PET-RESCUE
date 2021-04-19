package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Conversa;
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
    private Integer usuarioUm;
    private Integer usuarioDois;

    public ConversaDTO(Conversa conversa) {
        this.id = conversa.getId();
        this.usuarioUm = conversa.getUsuarioUm().getId();
        this.usuarioDois = conversa.getUsuarioDois().getId();
    }
}
