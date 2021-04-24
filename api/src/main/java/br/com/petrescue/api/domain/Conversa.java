package br.com.petrescue.api.domain;

import br.com.petrescue.api.controller.dto.ConversaDTO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "conversa")
public class Conversa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "usuario_um")
    @ManyToOne
    private Usuario usuarioUm;

    @JoinColumn(name = "usuario_dois")
    @ManyToOne
    private Usuario usuarioDois;

    public Conversa(ConversaDTO conversaDTO) {
    }
}
