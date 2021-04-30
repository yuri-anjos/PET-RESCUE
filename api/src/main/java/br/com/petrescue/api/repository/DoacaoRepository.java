package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Doacao;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DoacaoRepository extends PagingAndSortingRepository<Doacao, Integer> {

    List<Doacao> findByVaquinhaId(Integer idvaquinha);
}
