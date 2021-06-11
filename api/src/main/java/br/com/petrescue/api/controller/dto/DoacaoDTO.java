package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Doacao;
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
public class DoacaoDTO {

    private Integer id;
    private Double quantia;
    private Date quando;
    private Integer idDoador;
    private String nomeDoador;
    private Integer idVaquinha;

    public DoacaoDTO(Doacao doacao) {
        this.id = doacao.getId();
        this.quantia = doacao.getQuantia();
        this.quando = Date.from(doacao.getQuando().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
        this.idDoador = doacao.getDoador().getId();
        this.nomeDoador = doacao.getDoador().getNome();
        this.idVaquinha = doacao.getVaquinha().getId();
    }
}
