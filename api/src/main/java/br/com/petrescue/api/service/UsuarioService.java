package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.UsuarioDTO;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.enums.TipoUsuario;
import br.com.petrescue.api.exceptions.NegocioException;
import br.com.petrescue.api.repository.UsuarioRepository;
import br.com.petrescue.api.validator.GeralValidator;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GeralValidator geralValidator;

    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario=new Usuario(usuarioDTO);
        if ("INSTITUCIONAL".equals(usuario.getTipoUsuario())){
            this.geralValidator.string(usuario.getDescricao(), "Descrição de ONG/instituição");
            this.geralValidator.string(usuario.getCpfCnpj(), "CPF/CNPJ");
            this.geralValidator.string(usuario.getNomeOng(), "Nome de ONG/instituição");
            usuario.setTipoUsuario(TipoUsuario.INSTITUCIONAL);
        }
        usuario.setTipoUsuario(TipoUsuario.INDIVIDUO);
        usuario.setSaldo(0.0);
        return new UsuarioDTO(this.usuarioRepository.save(usuario));
    }

    public UsuarioDTO loginUsuario(UsuarioDTO usuarioDTO) {
        Optional<Usuario> opt = this.usuarioRepository.findByEmail(usuarioDTO.getEmail());
        if (opt.isEmpty()){
            throw new NegocioException("Login Inválido!");
        }
        return new UsuarioDTO(opt.get());
    }
}
