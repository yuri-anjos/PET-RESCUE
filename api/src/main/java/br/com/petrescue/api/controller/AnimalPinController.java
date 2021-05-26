package br.com.petrescue.api.controller;

import br.com.petrescue.api.domain.subClasses.Localizacao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.petrescue.api.controller.dto.AnimalPINDTO;
import br.com.petrescue.api.service.AnimalPinService;

@RestController
@RequestMapping("/animalpin")
public class AnimalPinController {

    @Autowired
    private AnimalPinService animalPinService;

    @PostMapping("/buscar")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalPINDTO> buscarAnimaisPin(@RequestBody Localizacao localizacao){
        return this.animalPinService.buscarAnimaisPin(localizacao);
    }

    @GetMapping("/usuario/{idusuario}")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalPINDTO> buscarAnimaisPinUsuarioId(@PathVariable("idusuario") Integer idusuario){
        return this.animalPinService.buscarAnimaisPinUsuarioId(idusuario);
    }

    @GetMapping("/{idanimalpin}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalPINDTO buscarAnimalPinId(@PathVariable("idanimalpin") Integer idanimalpin){
        return this.animalPinService.buscarAnimalPinId(idanimalpin);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalPINDTO cadastrarAnimalPIN(@RequestBody AnimalPINDTO animalPINDTO){
        return this.animalPinService.cadastrarAnimalPIN(animalPINDTO);
    }

    @PutMapping("/editar")
    @ResponseStatus(HttpStatus.OK)
    public AnimalPINDTO editarAnimalPIN(@RequestBody AnimalPINDTO animalPINDTO){
        return this.animalPinService.editarAnimalPIN(animalPINDTO);
    }

}
