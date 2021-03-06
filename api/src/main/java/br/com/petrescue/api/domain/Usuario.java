package br.com.petrescue.api.domain;

import br.com.petrescue.api.controller.dto.UsuarioDTO;
import br.com.petrescue.api.domain.enums.TipoUsuario;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition="DECIMAL(8,2) UNSIGNED")
    private Double saldo;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @Column
    @Lob
    private String foto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    @Column(name = "cpf_cnpj", unique = true)
    private String cpfCnpj;

    @Column
    private String descricao;

    public Usuario(UsuarioDTO usuarioDTO) {
        this.id = usuarioDTO.getId();
        this.saldo = usuarioDTO.getSaldo();
        this.email = usuarioDTO.getEmail();
        this.senha = usuarioDTO.getSenha();
        this.nome = usuarioDTO.getNome();
        this.foto = usuarioDTO.getFoto();
        this.tipoUsuario = usuarioDTO.getTipoUsuario();
        this.cpfCnpj = usuarioDTO.getCpfCnpj();
        this.descricao = usuarioDTO.getDescricao();
    }
}
