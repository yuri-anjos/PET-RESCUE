package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Conversa;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConversaRepository extends PagingAndSortingRepository<Conversa, Integer> {

}
