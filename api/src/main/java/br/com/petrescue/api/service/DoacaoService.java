package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.DoacaoDTO;
import br.com.petrescue.api.domain.Doacao;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.Vaquinha;
import br.com.petrescue.api.exceptions.NegocioException;
import br.com.petrescue.api.repository.DoacaoRepository;
import br.com.petrescue.api.repository.UsuarioRepository;
import br.com.petrescue.api.repository.VaquinhaRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private VaquinhaRepository vaquinhaRepository;

    @Transactional
    public void doarParaVaquinha(DoacaoDTO doacaoDTO){
        Doacao doacao = new Doacao(doacaoDTO);
        Usuario doador = this.usuarioRepository.findById(doacaoDTO.getDoador()).orElseThrow(() -> new NegocioException("Usuario doador não encontrado!"));
        Vaquinha vaquinha = this.vaquinhaRepository.findById(doacaoDTO.getVaquinha()).orElseThrow(() -> new NegocioException("Vaquinha não encontrada!"));
        Usuario recebedor = this.usuarioRepository.findById(vaquinha.getUsuario().getId()).orElseThrow(() -> new NegocioException("Responsável pela vaqinha não encontrado!"));

        doador.setSaldo(doador.getSaldo() - doacao.getQuantia());
        if(doador.getSaldo()<0){
            throw new NegocioException("Doador não possui o saldo suficiente!");
        }

        vaquinha.setValorArrecadado(vaquinha.getValorArrecadado() + doacao.getQuantia());
        if(vaquinha.getMeta() != -1 && vaquinha.getValorArrecadado()>=vaquinha.getMeta()){
            vaquinha.setAtivo(false);
        }

        recebedor.setSaldo(recebedor.getSaldo() + doacao.getQuantia());
        doacao.setQuando(LocalDateTime.now());
        doacao.setDoador(doador);
        doacao.setVaquinha(vaquinha);

        this.vaquinhaRepository.save(vaquinha);
        this.doacaoRepository.save(doacao);
        this.usuarioRepository.save(doador);
        this.usuarioRepository.save(recebedor);
    }
}
