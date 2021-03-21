package br.com.petrescue.api.controller;


import br.com.petrescue.api.domain.Individuo;
import br.com.petrescue.api.domain.Instituicao;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.service.BuscarTodosUsuariosService;
import br.com.petrescue.api.service.CriarTodosUsuariosService;
import br.com.petrescue.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

//    @Autowired
//    private CadastrarFeedbackService cadastrarFeedbackService;
//
//    @Autowired
//    private RealizarFeedbackService realizarFeedbackService;
//
//    @PutMapping("/reagendar/{idfeedback}")
//    @ResponseStatus(HttpStatus.OK)
//    public FeedbackResponse reagendar(@PathVariable Integer idfeedback, @RequestBody @Valid FeedbackDataRequest feedbackDataRequest) {
//        return reagendarFeedbackService.reagendar(idfeedback, feedbackDataRequest);
//    }
//
//    @GetMapping("/autor/{pagina}/{tamanho}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<FeedbackResponse> buscarFeedbacksHierarquia(@PathVariable int pagina, @PathVariable int tamanho, @RequestParam(required = false) Integer mes, @RequestParam(required = false) String status, @RequestParam(required = false) String avaliado, @RequestParam(required = false) Integer anual, @RequestParam(required = false) String autor) {
//        return buscarFeedbacksHierarquiaService.buscar(pagina, tamanho, mes, status, avaliado, anual, autor);
//    }

    @Autowired
    private BuscarTodosUsuariosService buscarTodosUsuariosService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/buscar/todos")
    @ResponseStatus(HttpStatus.OK)
    public void buscarTodosTiposUsuarios(){
        buscarTodosUsuariosService.buscar();
    }

    @PostMapping("/cadastrar/individuo")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrarIndividuo(@RequestBody Individuo individuo){
        return usuarioService.cadastrarIndividuo(individuo);
    }

    @PostMapping("/cadastrar/instituicao")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrarInstituicao(@RequestBody Instituicao instituicao){
        return usuarioService.cadastrarInstituicao(instituicao);
    }
}