package br.com.petrescue.api.repository;

import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.enums.TipoUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer> {

    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    List<Usuario> findByTipoUsuario(TipoUsuario institucional, Pageable pageable);
}
