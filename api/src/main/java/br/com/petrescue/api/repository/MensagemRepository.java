package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Mensagem;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MensagemRepository extends PagingAndSortingRepository<Mensagem, Integer> {

}
