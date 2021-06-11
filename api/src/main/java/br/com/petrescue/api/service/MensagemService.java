package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.MensagemDTO;
import br.com.petrescue.api.domain.Mensagem;
import br.com.petrescue.api.exceptions.NaoEncontradoException;
import br.com.petrescue.api.repository.ConversaRepository;
import br.com.petrescue.api.repository.MensagemRepository;
import br.com.petrescue.api.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConversaRepository conversaRepository;

    public List<MensagemDTO> buscarMensagensConversaId(Integer idconversa) {
        return this.mensagemRepository.findByConversaIdOrderByHorarioAsc(idconversa).stream().map(MensagemDTO::new).collect(Collectors.toList());
    }

    public MensagemDTO cadastrarMensagem(MensagemDTO mensagemDTO) {
        Mensagem mensagem = new Mensagem(mensagemDTO);
        mensagem.setAutor(this.usuarioRepository.findById(mensagemDTO.getIdAutor()).orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!")));
        mensagem.setConversa(this.conversaRepository.findById(mensagemDTO.getIdConversa()).orElseThrow(() -> new NaoEncontradoException("Erro ao buscar o chat!")));
        mensagem.setHorario(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return new MensagemDTO(this.mensagemRepository.save(mensagem));
    }
}
