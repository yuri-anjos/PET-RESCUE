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
        geralValidator.stringTamanho(individuo.getNome(), "Nome", 3);
        geralValidator.string(individuo.getEmail(), "email");

        individuo.setSaldo(0.0);
        individuo.setId(null);

        Individuo ind = individuoRepository.save(individuo);
        return usuarioRepository.findById(ind.getId()).get();
    }

    @Transactional
    public Usuario cadastrarInstituicao(Instituicao instituicao) {
        geralValidator.stringTamanho(instituicao.getNome(), "Nome", 3);
        geralValidator.string(instituicao.getEmail(), "email");
        geralValidator.stringTamanho(instituicao.getCpfCnpj(), "CPF/CNPJ", 11);
        geralValidator.stringTamanho(instituicao.getDescricao(), "Descrição", 10);
        geralValidator.localizacao(instituicao.getLocalizacao());
        instituicao.setSaldo(0.0);
        instituicao.setId(null);

        Instituicao inst = instituicaoRepository.save(instituicao);
        return usuarioRepository.findById(inst.getId()).get();
    }
}
