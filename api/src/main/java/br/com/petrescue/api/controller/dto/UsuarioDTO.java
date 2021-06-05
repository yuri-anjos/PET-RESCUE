package br.com.petrescue.api.controller.dto;

import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.enums.TipoUsuario;
import br.com.petrescue.api.domain.subClasses.Localizacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;
    private Double saldo;
    private String email;
    private String senha;
    private String nome;
    private String foto;

    private TipoUsuario tipoUsuario;

    private String cpfCnpj;
    private String descricao;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.saldo = usuario.getSaldo();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.nome = usuario.getNome();
        this.foto = usuario.getFoto();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.cpfCnpj = usuario.getCpfCnpj();
        this.descricao = usuario.getDescricao();
    }
}
