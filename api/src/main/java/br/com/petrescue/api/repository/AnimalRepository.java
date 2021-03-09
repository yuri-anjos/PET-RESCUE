package br.com.petrescue.api.repository;


import br.com.petrescue.api.domain.Animal;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnimalRepository extends PagingAndSortingRepository<Animal, Integer> {

}
