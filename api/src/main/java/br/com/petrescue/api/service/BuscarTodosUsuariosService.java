package br.com.petrescue.api.service;

import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuscarTodosUsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public List<Usuario> buscar(){
        return usuarioRepository.findAll();
    }
}
