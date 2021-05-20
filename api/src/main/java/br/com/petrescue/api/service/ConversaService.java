package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.ConversaDTO;
import br.com.petrescue.api.domain.Conversa;
import br.com.petrescue.api.exceptions.NaoEncontradoException;
import br.com.petrescue.api.repository.ConversaRepository;
import br.com.petrescue.api.repository.MensagemRepository;
import br.com.petrescue.api.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversaService {

    @Autowired
    private ConversaRepository conversaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    public Integer buscarConversaAmbosUsuarios(Integer idusuario1, Integer idusuario2) {
        Optional<Integer> optIdConversa = this.conversaRepository.buscarConversaAmbosUsuarios(idusuario1, idusuario2);
        if (optIdConversa.isPresent()) {
            return optIdConversa.get();
        }
        Conversa conversa = new Conversa();
        conversa.setUsuarioUm(this.usuarioRepository.findById(idusuario1).orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!")));
        conversa.setUsuarioDois(this.usuarioRepository.findById(idusuario2).orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!")));
        conversa = this.conversaRepository.save(conversa);
        return conversa.getId();
    }

    public ConversaDTO buscarConversaId(Integer idconversa, Integer idusuario) {
        Conversa conversa = this.conversaRepository.findById(idconversa).orElseThrow(() -> new NaoEncontradoException("Erro ao buscar o chat!"));
        return new ConversaDTO(idusuario, conversa);
    }

    public List<ConversaDTO> buscarConversasUsuarioId(Integer idusuario) {
        return this.conversaRepository.buscarConversasDeUsuario(idusuario).stream().map(obj -> new ConversaDTO(idusuario, obj, this.mensagemRepository.findFirstByConversaIdOrderByHorarioDesc(obj.getId()).orElse(null))).collect(Collectors.toList());
    }
}
