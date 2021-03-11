package br.com.petrescue.api.repository;


import br.com.petrescue.api.domain.Individuo;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IndividuoRepository extends PagingAndSortingRepository<Individuo, Integer> {
    Individuo save(Individuo individuo);
    List<Individuo> findAll();
}
