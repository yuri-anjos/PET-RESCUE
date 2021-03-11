package br.com.petrescue.api.repository;


import br.com.petrescue.api.domain.Usuario;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer> {

//    boolean existsByUsernameAndCargoNot(String usuario, Cargo cargo);
//
//    Optional<Usuario> findByUsername(String usuario);
//
//    Optional<Usuario> findById(Integer id);
//
//    boolean existsById(Integer id);
//
//    List<Usuario> findByDataIngressoLessThanEqualOrDataIngressoGreaterThanEqualAndCargoNot(LocalDate dataAntes, LocalDate data, Cargo cargo);
//
//    List<Usuario> findByDataIngressoBetweenAndCargoNot(LocalDate dataAntes, LocalDate data, Cargo cargo);

    List<Usuario> findAll();
}
