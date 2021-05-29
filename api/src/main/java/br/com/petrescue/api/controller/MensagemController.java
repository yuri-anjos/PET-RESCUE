package br.com.petrescue.api.controller;

import br.com.petrescue.api.controller.dto.MensagemDTO;
import br.com.petrescue.api.service.MensagemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @GetMapping("/{idconversa}")
    @ResponseStatus(HttpStatus.OK)
    public List<MensagemDTO> buscarMensagensConversaId(@PathVariable("idconversa") Integer idconversa) {
        return this.mensagemService.buscarMensagensConversaId(idconversa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MensagemDTO cadastrarMensagem(@RequestBody MensagemDTO mensagemDTO) {
        return this.mensagemService.cadastrarMensagem(mensagemDTO);
    }
}
