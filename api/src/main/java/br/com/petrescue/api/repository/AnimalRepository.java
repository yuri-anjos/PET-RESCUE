package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Animal;
import br.com.petrescue.api.domain.enums.SituacaoAdocao;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnimalRepository extends PagingAndSortingRepository<Animal, Integer> {

    List<Animal> findByUsuarioId(Integer idUsuario);

    List<Animal> findBySituacaoAdocao(SituacaoAdocao situacaoAdocao, Pageable pageable);
}
