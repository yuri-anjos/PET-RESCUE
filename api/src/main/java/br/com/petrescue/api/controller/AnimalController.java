package br.com.petrescue.api.controller;

import br.com.petrescue.api.controller.dto.AnimalDTO;
import br.com.petrescue.api.service.AnimalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalDTO> buscarAnimaisAdocao(@RequestParam("pg") Integer pg){
        return this.animalService.buscarAnimaisAdocao(pg);
    }

    @GetMapping("/usuario/{idusuario}")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalDTO> buscarAnimaisAdocaoUsuarioId(@PathVariable("idusario") Integer idusuario){
        return this.animalService.buscarAnimaisAdocaoUsuarioId(idusuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalDTO cadastrarAnimalAdocao(@RequestBody AnimalDTO animalDTO){
        return this.animalService.cadastrarAnimalAdocao(animalDTO);
    }

    @PostMapping("/{idanimal}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalDTO buscarAnimaisAdocaoId(@PathVariable("idanimal") Integer idanimal){
        return this.animalService.buscarAnimalDoacaoId(idanimal);
    }
}
