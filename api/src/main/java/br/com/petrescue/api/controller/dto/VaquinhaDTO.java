package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Vaquinha;
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
public class VaquinhaDTO {

    private Integer id;
    private Double meta;
    private Double valorArrecadado;
    private String foto;
    private Date inicio;
    private String titulo;
    private String descricao;
    private Boolean ativo;
    private Integer idUsuario;
    private String nomeUsuario;

    public VaquinhaDTO(Vaquinha vaquinha) {
        this.id = vaquinha.getId();
        this.meta = vaquinha.getMeta();
        this.valorArrecadado = vaquinha.getValorArrecadado();
        this.foto = vaquinha.getFoto();
        this.inicio = Date.from(vaquinha.getInicio().atStartOfDay(ZoneId.of("America/Sao_Paulo")).toInstant());
        this.titulo = vaquinha.getTitulo();
        this.descricao = vaquinha.getDescricao();
        this.ativo = vaquinha.getAtivo();
        this.idUsuario = vaquinha.getUsuario().getId();
        this.nomeUsuario = vaquinha.getUsuario().getNome();
    }
}
