package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Vaquinha;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VaquinhaRepository extends PagingAndSortingRepository<Vaquinha, Integer> {

    List<Vaquinha> findByUsuarioId(Integer idUsuario);

    List<Vaquinha> findByAtivo(boolean ativo, Pageable pageable);
}
