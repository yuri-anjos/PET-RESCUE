package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.VaquinhaDTO;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.Vaquinha;
import br.com.petrescue.api.domain.enums.TipoUsuario;
import br.com.petrescue.api.exceptions.NaoEncontradoException;
import br.com.petrescue.api.exceptions.NegocioException;
import br.com.petrescue.api.repository.UsuarioRepository;
import br.com.petrescue.api.repository.VaquinhaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VaquinhaService {

    @Autowired
    private VaquinhaRepository vaquinhaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<VaquinhaDTO> buscarVaquinhas(Integer pg){
        Pageable pageable = PageRequest.of(pg, 10);
        return this.vaquinhaRepository.findByAtivo(true, pageable).stream().map(VaquinhaDTO::new).collect(Collectors.toList());
    }

    public VaquinhaDTO buscarVaquinhaId(Integer idvaquinha){
        return new VaquinhaDTO(this.vaquinhaRepository.findById(idvaquinha).orElseThrow(() -> new NaoEncontradoException("Vaquinha não encontrada!")));
    }

    public List<VaquinhaDTO> buscarVaquinhasUsuarioId(Integer idusario){
        return this.vaquinhaRepository.findByUsuarioId(idusario).stream().map(VaquinhaDTO::new).collect(Collectors.toList());
    }

    public VaquinhaDTO cadastrarVaquinha(VaquinhaDTO vaquinhaDTO){
        Vaquinha vaquinha = new Vaquinha(vaquinhaDTO);
        Usuario usuario = this.usuarioRepository.findById(vaquinhaDTO.getUsuario()).orElseThrow(()->new NaoEncontradoException("Usuário não encontrado!"));

        if(vaquinha.getMeta() == -1 && TipoUsuario.INDIVIDUO.equals(usuario.getTipoUsuario())){
            throw new NegocioException("Apenas instituições podem utilizar de arregadações sem limite!");
        }

        vaquinha.setUsuario(usuario);
        vaquinha.setAtivo(true);
        vaquinha.setInicio(LocalDate.now());
        return new VaquinhaDTO(this.vaquinhaRepository.save(vaquinha));
    }
}
