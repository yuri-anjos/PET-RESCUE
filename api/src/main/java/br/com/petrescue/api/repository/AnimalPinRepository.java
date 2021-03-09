package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.AnimalPIN;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnimalPinRepository extends PagingAndSortingRepository<AnimalPIN, Integer> {

}
