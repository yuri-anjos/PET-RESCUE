package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Vaquinha;
import java.time.LocalDate;
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
    private LocalDate inicio;
    private String titulo;
    private String descricao;
    private Boolean ativo;
    private Integer usuario;

    public VaquinhaDTO(Vaquinha vaquinha) {
        this.id = vaquinha.getId();
        this.meta = vaquinha.getMeta();
        this.valorArrecadado = vaquinha.getValorArrecadado();
        this.foto = vaquinha.getFoto();
        this.inicio = vaquinha.getInicio();
        this.titulo = vaquinha.getTitulo();
        this.descricao = vaquinha.getDescricao();
        this.ativo = vaquinha.getAtivo();
        this.usuario = vaquinha.getUsuario().getId();
    }
}
