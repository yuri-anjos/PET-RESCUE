package br.com.petrescue.api.controller;


import br.com.petrescue.api.service.BuscarTodosUsuariosService;
import br.com.petrescue.api.service.CriarTodosUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/privado/usuario")
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
    private CriarTodosUsuariosService criarTodosUsuariosService;

    @Autowired
    private BuscarTodosUsuariosService buscarTodosUsuariosService;

    @GetMapping("/cadastro/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public String criarTodosTiposUsuarios(){
        criarTodosUsuariosService.criar();
        return "criado com sucesso";
    }

    @GetMapping("/buscar/todos")
    @ResponseStatus(HttpStatus.OK)
    public void buscarTodosTiposUsuarios(){
        buscarTodosUsuariosService.buscar();
    }
}