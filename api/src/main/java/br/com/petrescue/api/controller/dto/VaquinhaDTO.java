package br.com.petrescue.api.controller.dto;

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
}
