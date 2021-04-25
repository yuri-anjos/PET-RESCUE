package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Doacao;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoacaoDTO {

    private Integer id;
    private Double quantia;
    private LocalDateTime quando;
    private Integer doador;
    private Integer vaquinha;

    public DoacaoDTO(Doacao doacao) {
        this.id = doacao.getId();
        this.quantia = doacao.getQuantia();
        this.quando = doacao.getQuando();
        this.doador = doacao.getDoador().getId();
        this.vaquinha = doacao.getVaquinha().getId();
    }
}
