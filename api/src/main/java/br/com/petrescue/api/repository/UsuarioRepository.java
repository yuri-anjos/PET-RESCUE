package br.com.petrescue.api.repository;


import br.com.petrescue.api.domain.Usuario;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer> {

    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
