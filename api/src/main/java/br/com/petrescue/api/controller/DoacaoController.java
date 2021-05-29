package br.com.petrescue.api.controller;

import br.com.petrescue.api.controller.dto.DoacaoDTO;
import br.com.petrescue.api.service.DoacaoService;
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
@RequestMapping("/doacao")
public class DoacaoController {

    @Autowired
    private DoacaoService doacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void doarParaVaquinha(@RequestBody DoacaoDTO doacaoDTO) {
        this.doacaoService.doarParaVaquinha(doacaoDTO);
    }

    @GetMapping("/vaquinha/{idvaquinha}")
    @ResponseStatus(HttpStatus.OK)
    public List<DoacaoDTO> buscarDoacoesVaquinhaId(@PathVariable("idvaquinha") Integer idvaquinha) {
        return this.doacaoService.buscarDoacoesVaquinha(idvaquinha);
    }

}
