package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Conversa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConversaRepository extends PagingAndSortingRepository<Conversa, Integer> {

    @Query("select c.id from Conversa c " +
            "where " +
            "(c.usuarioUm.id = ?1 or c.usuarioUm.id = ?2) and " +
            "(c.usuarioDois.id = ?1 or c.usuarioDois.id = ?2) ")
    Optional<Integer> buscarConversaAmbosUsuarios(Integer id1, Integer id2);

    @Query("select c from Conversa c " +
            "where " +
            "c.usuarioUm.id = ?1 or c.usuarioDois.id = ?1")
    List<Conversa> buscarConversasDeUsuario(Integer idusuario);
}
