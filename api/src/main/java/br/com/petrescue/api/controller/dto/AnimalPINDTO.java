package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.AnimalPIN;
import br.com.petrescue.api.domain.enums.TipoAnimal;
import br.com.petrescue.api.domain.enums.TipoPIN;
import br.com.petrescue.api.domain.subClasses.Localizacao;
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
public class AnimalPINDTO {

    private Integer id;
    private String foto;
    private String descricao;
    private TipoAnimal tipoAnimal;
    private String raca;
    private TipoPIN tipoPIN;
    private Boolean ativo;
    private Localizacao localizacao;
    private Date dataCadastro;
    private Integer idUsuario;
    private String nomeUsuario;

    public AnimalPINDTO(AnimalPIN animalPIN) {
        this.id = animalPIN.getId();
        this.foto = animalPIN.getFoto();
        this.descricao = animalPIN.getDescricao();
        this.tipoAnimal = animalPIN.getTipoAnimal();
        this.raca = animalPIN.getRaca();
        this.tipoPIN = animalPIN.getTipoPIN();
        this.ativo = animalPIN.getAtivo();
        this.localizacao = animalPIN.getLocalizacao();
        this.dataCadastro = Date.from(animalPIN.getDataCadastro().atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.idUsuario = animalPIN.getUsuario().getId();
        this.nomeUsuario = animalPIN.getUsuario().getNome();
    }
}
