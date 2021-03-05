//package br.com.petrescue.api.service;
//
//import br.com.cwi.crescer.api.controller.response.UsuarioResponse;
//import br.com.cwi.crescer.api.domain.Usuario;
//import br.com.cwi.crescer.api.domain.enums.Cargo;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EncontrarUsuarioLogadoResponseService {
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private EncontrarUsuarioLogadoService encontrarUsuarioLogadoService;
//
//    public UsuarioResponse encontrar() {
//        Usuario usuario = encontrarUsuarioLogadoService.encontrar();
//        UsuarioResponse usuarioResponse = modelMapper.map(usuario, UsuarioResponse.class);
//
//        if (!usuario.getCargo().equals(Cargo.GERENTE)) {
//            usuarioResponse.setIdResponsavel(usuario.getResponsavel().getId());
//        }
//
//        return usuarioResponse;
//    }
//}
