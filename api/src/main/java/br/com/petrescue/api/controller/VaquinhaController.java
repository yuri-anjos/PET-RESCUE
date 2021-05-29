package br.com.petrescue.api.controller;

import br.com.petrescue.api.controller.dto.VaquinhaDTO;
import br.com.petrescue.api.service.VaquinhaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vaquinha")
public class VaquinhaController {

    @Autowired
    private VaquinhaService vaquinhaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VaquinhaDTO> buscarVaquinhas(@RequestParam("pg") Integer pg) {
        return this.vaquinhaService.buscarVaquinhas(pg);
    }

    @GetMapping("/usuario/{idusuario}")
    @ResponseStatus(HttpStatus.OK)
    public List<VaquinhaDTO> buscarVaquinhasUsuarioId(@PathVariable("idusuario") Integer idusuario) {
        return this.vaquinhaService.buscarVaquinhasUsuarioId(idusuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VaquinhaDTO cadastrarVaquinha(@RequestBody VaquinhaDTO vaquinhaDTO) {
        return this.vaquinhaService.cadastrarVaquinha(vaquinhaDTO);
    }

    @PutMapping("/editar")
    @ResponseStatus(HttpStatus.OK)
    public VaquinhaDTO editarVaquinha(@RequestBody VaquinhaDTO vaquinhaDTO) {
        return this.vaquinhaService.editarVaquinha(vaquinhaDTO);
    }

    @GetMapping("/{idvaquinha}")
    @ResponseStatus(HttpStatus.OK)
    public VaquinhaDTO buscarVaquinhaId(@PathVariable("idvaquinha") Integer idvaquinha) {
        return this.vaquinhaService.buscarVaquinhaId(idvaquinha);
    }
}
