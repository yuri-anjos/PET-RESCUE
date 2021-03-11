package br.com.petrescue.api.service;

import br.com.petrescue.api.domain.Individuo;
import br.com.petrescue.api.domain.Instituicao;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.repository.IndividuoRepository;
import br.com.petrescue.api.repository.InstituicaoRepository;
import br.com.petrescue.api.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuscarTodosUsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private IndividuoRepository individuoRepository;

    @Transactional
    public void buscar(){
        List<Usuario> usuarios=usuarioRepository.findAll();
        List<Instituicao> instituicoes=instituicaoRepository.findAll();
        List<Individuo> individuos=individuoRepository.findAll();
        return;
    }
}
