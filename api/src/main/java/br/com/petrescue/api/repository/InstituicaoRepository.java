package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Instituicao;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InstituicaoRepository extends PagingAndSortingRepository<Instituicao, Integer> {

}
