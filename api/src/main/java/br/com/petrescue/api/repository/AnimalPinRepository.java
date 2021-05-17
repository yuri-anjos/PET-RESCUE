package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.AnimalPIN;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnimalPinRepository extends PagingAndSortingRepository<AnimalPIN, Integer> {

    Optional<AnimalPIN> findByUsuarioId(Integer idusuario);

    @Query("SELECT a " +
            "FROM AnimalPIN a where " +
            "(6371 * " +
            "acos (" +
            "cos(radians(?1)) * " +
            "cos(radians(a.localizacao.latitude)) * " +
            "cos(radians(?2) - radians(a.localizacao.longitude)) + " +
            "sin(radians(?1)) * " +
            "sin(radians(a.localizacao.latitude)) " +
            ")) <= ?3 and a.ativo = true")
    List<AnimalPIN> buscarPinDentroDeRaio(Double lat, Double lng, Double raio);
}
