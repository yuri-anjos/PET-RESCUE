package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Mensagem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MensagemRepository extends PagingAndSortingRepository<Mensagem, Integer> {

    Optional<Mensagem> findFirstByConversaIdOrderByHorarioDesc(Integer id);

    List<Mensagem> findByConversaIdOrderByHorarioAsc(Integer idconversa);
}
