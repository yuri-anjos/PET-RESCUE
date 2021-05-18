package br.com.petrescue.api.controller;

import br.com.petrescue.api.controller.dto.ConversaDTO;
import br.com.petrescue.api.service.ConversaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conversa")
public class ConversaController {

    @Autowired
    private ConversaService cadastrarUsuario;

    @GetMapping("/{idusuario1}/{idusuario2}")
    @ResponseStatus(HttpStatus.OK)
    public Integer buscarConversaAmbosUsuarios(@PathVariable("idusuario1") Integer idusuario1, @PathVariable("idusuario2") Integer idusuario2){
        return this.cadastrarUsuario.buscarConversaAmbosUsuarios(idusuario1, idusuario2);
    }

    @GetMapping("/{idconversa}")
    @ResponseStatus(HttpStatus.OK)
    public ConversaDTO buscarConversaId(@PathVariable("idconversa") Integer idconversa){
        return this.cadastrarUsuario.buscarConversaId(idconversa);
    }

    @GetMapping("/usuario/{idusuario}")
    @ResponseStatus(HttpStatus.OK)
    public List<ConversaDTO> buscarConversasUsuarioId(@PathVariable("idusuario") Integer idusuario){
        return this.cadastrarUsuario.buscarConversasUsuarioId(idusuario);
    }
}
