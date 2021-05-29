package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.AnimalPINDTO;
import br.com.petrescue.api.domain.AnimalPIN;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.subClasses.Localizacao;
import br.com.petrescue.api.exceptions.NaoEncontradoException;
import br.com.petrescue.api.repository.AnimalPinRepository;
import br.com.petrescue.api.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalPinService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnimalPinRepository animalPinRepository;

    public List<AnimalPINDTO> buscarAnimaisPin(Localizacao localizacao) {
        return this.animalPinRepository.buscarPinDentroDeRaio(localizacao.getLatitude(), localizacao.getLongitude(), 15.0).stream().map(AnimalPINDTO::new).collect(Collectors.toList());
    }

    public List<AnimalPINDTO> buscarAnimaisPinUsuarioId(Integer idusuario) {
        return this.animalPinRepository.findByUsuarioId(idusuario).stream().map(AnimalPINDTO::new).collect(Collectors.toList());
    }

    public AnimalPINDTO buscarAnimalPinId(Integer idanimalpin) {
        return new AnimalPINDTO(this.animalPinRepository.findById(idanimalpin).orElseThrow(() -> new NaoEncontradoException("Animal não encontrado!")));
    }

    public AnimalPINDTO cadastrarAnimalPIN(AnimalPINDTO animalPinDTO) {
        AnimalPIN animalPIN = new AnimalPIN(animalPinDTO);
        Usuario usuario = this.usuarioRepository.findById(animalPinDTO.getIdUsuario()).orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!"));

        animalPIN.setAtivo(true);
        animalPIN.setDataCadastro(LocalDate.now());
        animalPIN.setUsuario(usuario);
        return new AnimalPINDTO(this.animalPinRepository.save(animalPIN));
    }

    public AnimalPINDTO editarAnimalPIN(AnimalPINDTO animalPinDTO) {
        AnimalPIN animalPIN = new AnimalPIN(animalPinDTO);
        Usuario usuario = this.usuarioRepository.findById(animalPinDTO.getIdUsuario())
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!"));
        animalPIN.setUsuario(usuario);
        return new AnimalPINDTO(this.animalPinRepository.save(animalPIN));
    }

}
