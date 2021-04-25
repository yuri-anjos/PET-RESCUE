package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.AnimalDTO;
import br.com.petrescue.api.domain.Animal;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.enums.SituacaoAdocao;
import br.com.petrescue.api.exceptions.NegocioException;
import br.com.petrescue.api.repository.AnimalRepository;
import br.com.petrescue.api.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<AnimalDTO> buscarAnimaisAdocao(Integer pg){
        Pageable pageable = PageRequest.of(pg, 10);
        return this.animalRepository.findAll(pageable).stream().map(AnimalDTO::new).collect(Collectors.toList());
    }

    public AnimalDTO buscarAnimalDoacaoId(Integer idanimal){
        return new AnimalDTO(this.animalRepository.findById(idanimal).orElseThrow(() -> new NegocioException("Animal para adoção inválido")));
    }

    public List<AnimalDTO> buscarAnimaisAdocaoUsuarioId(Integer idusario){
        return this.animalRepository.findByUsuarioId(idusario).stream().map(AnimalDTO::new).collect(Collectors.toList());
    }

    public AnimalDTO cadastrarAnimalAdocao(AnimalDTO animalDTO){
        Animal animal = new Animal(animalDTO);
        Usuario usuario = this.usuarioRepository.findById(animalDTO.getUsuario()).orElseThrow(()->new NegocioException("Usuário inválido."));
        animal.setUsuario(usuario);
        animal.setSituacaoAdocao(SituacaoAdocao.ESPERA);
        return new AnimalDTO(this.animalRepository.save(animal));
    }

}
