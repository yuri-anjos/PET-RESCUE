package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Vaquinha;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VaquinhaRepository extends PagingAndSortingRepository<Vaquinha, Integer> {

}
