package br.com.petrescue.api.service;

import br.com.petrescue.api.domain.Individuo;
import br.com.petrescue.api.domain.Instituicao;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.repository.IndividuoRepository;
import br.com.petrescue.api.repository.InstituicaoRepository;
import br.com.petrescue.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriarTodosUsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private IndividuoRepository individuoRepository;

    @Transactional
    public void criar(){
        Instituicao instituicao=new Instituicao();
        instituicao.setNome("Yuri França");
        instituicao.setEmail("yuri.anjos16@gmail.com");
        instituicao.setSaldo(0.0);
        instituicao.setCpfCnpj("04005108040");
        instituicao.setDescricao("instituto");

        Individuo individuo=new Individuo();
        individuo.setNome("Yuri França");
        individuo.setEmail("yuri.anjos17@gmail.com");
        individuo.setSaldo(0.0);

        instituicaoRepository.save(instituicao);
        individuoRepository.save(individuo);
    }
}
