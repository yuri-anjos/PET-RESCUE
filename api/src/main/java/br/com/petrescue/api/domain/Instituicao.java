package br.com.petrescue.api.domain;

import br.com.petrescue.api.domain.subClasses.Localizacao;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "instituicao")
public class Instituicao extends Usuario{

    @Column(name = "cpf_cnpj", unique = true, nullable = false)
    private String cpfCnpj;

    @Column
    private String descricao;

    public Instituicao(Integer id, Double saldo, String email, String nome, String foto, Localizacao localizacao, String cpfCnpj, String descricao) {
        super(id, saldo, email, nome, foto, localizacao);
        this.cpfCnpj = cpfCnpj;
        this.descricao = descricao;
    }
}
