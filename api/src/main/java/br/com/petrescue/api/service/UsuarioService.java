package br.com.petrescue.api.service;

import br.com.petrescue.api.domain.Individuo;
import br.com.petrescue.api.domain.Instituicao;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.repository.IndividuoRepository;
import br.com.petrescue.api.repository.InstituicaoRepository;
import br.com.petrescue.api.repository.UsuarioRepository;
import br.com.petrescue.api.validator.GeralValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IndividuoRepository individuoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private GeralValidator geralValidator;

    @Transactional
    public Usuario cadastrarIndividuo(Individuo individuo) {
        geralValidator.string(individuo.getNome(), "Nome");
        geralValidator.string(individuo.getEmail(), "email");

        individuo.setSaldo(0.0);
        individuo.setId(null);

        Individuo ind = individuoRepository.save(individuo);
        return usuarioRepository.findById(ind.getId()).get();
    }

    @Transactional
    public Usuario cadastrarInstituicao(Instituicao instituicao) {
        geralValidator.string(instituicao.getNome(), "Nome");
        geralValidator.string(instituicao.getEmail(), "email");
        geralValidator.string(instituicao.getCpfCnpj(), "CPF/CNPJ");
        geralValidator.string(instituicao.getDescricao(), "Descrição");
        geralValidator.string(instituicao.getNomeOng(), "Nome da ONG");
        geralValidator.localizacao(instituicao.getLocalizacao());

        instituicao.setSaldo(0.0);
        instituicao.setId(null);

        Instituicao inst = instituicaoRepository.save(instituicao);
        return usuarioRepository.findById(inst.getId()).get();
    }
}
