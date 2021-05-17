package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.AnimalDTO;
import br.com.petrescue.api.domain.Animal;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.enums.SituacaoAdocao;
import br.com.petrescue.api.exceptions.NaoEncontradoException;
import br.com.petrescue.api.exceptions.NegocioException;
import br.com.petrescue.api.repository.AnimalRepository;
import br.com.petrescue.api.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<AnimalDTO> buscarAnimaisAdocao(Integer pg) {
        Pageable pageable = PageRequest.of(pg, 10, Sort.by("situacaoAdocao").descending());
        return this.animalRepository.findAll(pageable).stream().map(AnimalDTO::new).collect(Collectors.toList());
    }

    public AnimalDTO buscarAnimalAdocaoId(Integer idanimal) {
        return new AnimalDTO(this.animalRepository.findById(idanimal).orElseThrow(() -> new NaoEncontradoException("Animal não encontrado!")));
    }

    public List<AnimalDTO> buscarAnimaisAdocaoUsuarioId(Integer idusuario) {
        return this.animalRepository.findByUsuarioId(idusuario).stream().map(AnimalDTO::new).collect(Collectors.toList());
    }

    public AnimalDTO cadastrarAnimalAdocao(AnimalDTO animalDTO) {
        Animal animal = new Animal(animalDTO);
        Usuario usuario = this.usuarioRepository.findById(animalDTO.getIdUsuario()).orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!"));
        animal.setUsuario(usuario);
        animal.setSituacaoAdocao(SituacaoAdocao.ESPERA);
        return new AnimalDTO(this.animalRepository.save(animal));
    }

    public AnimalDTO editarAnimalAdocao(AnimalDTO animalDTO) {
        Animal animal = new Animal(animalDTO);
        Usuario usuario = this.usuarioRepository.findById(animalDTO.getIdUsuario()).orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!"));

        if (SituacaoAdocao.ADOTADO.equals(animal.getSituacaoAdocao())) {
            throw new NegocioException("Animal ADOTADO não pode ser alterado!");
        }

        animal.setUsuario(usuario);
        return new AnimalDTO(this.animalRepository.save(animal));
    }

    public AnimalDTO adotarAnimal(Integer idanimal) {
        Animal animal = this.animalRepository.findById(idanimal).orElseThrow(() -> new NaoEncontradoException("Animal não encontrado!"));
        animal.setSituacaoAdocao(SituacaoAdocao.ADOTADO);
        return new AnimalDTO(this.animalRepository.save(animal));
    }
}
