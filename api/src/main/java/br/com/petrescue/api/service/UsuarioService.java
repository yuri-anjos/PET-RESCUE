package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.CarteiraDTO;
import br.com.petrescue.api.controller.dto.UsuarioDTO;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.enums.TipoUsuario;
import br.com.petrescue.api.exceptions.NaoEncontradoException;
import br.com.petrescue.api.exceptions.NegocioException;
import br.com.petrescue.api.repository.UsuarioRepository;
import br.com.petrescue.api.utils.GeralValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GeralValidator geralValidator;

    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        if (TipoUsuario.INSTITUCIONAL.equals(usuario.getTipoUsuario())) {
            this.geralValidator.string(usuario.getDescricao(), "Descrição de ONG/instituição");
            this.geralValidator.string(usuario.getCpfCnpj(), "CPF/CNPJ");
            this.geralValidator.string(usuario.getNomeOng(), "Nome de ONG/instituição");
//            this.geralValidator.localizacao(usuario.getLocalizacao());
        }
        usuario.setSaldo(0.0);
        return new UsuarioDTO(this.usuarioRepository.save(usuario));
    }

    public UsuarioDTO loginUsuario(UsuarioDTO usuarioDTO) {
        return new UsuarioDTO(this.usuarioRepository.findByEmailAndSenha(usuarioDTO.getEmail(), usuarioDTO.getSenha()).orElseThrow(() -> new NegocioException("Login Inválido!")));
    }

    public UsuarioDTO buscarUsuarioId(Integer idusuario) {
        return new UsuarioDTO(this.usuarioRepository.findById(idusuario).orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!")));
    }

    public UsuarioDTO depositarSaldo(CarteiraDTO carteiraDTO) {
        Usuario usuario = this.usuarioRepository.findById(carteiraDTO.getIdUsuario()).orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!"));
        usuario.setSaldo(usuario.getSaldo() + carteiraDTO.getSaldoAdicional());
        return new UsuarioDTO(this.usuarioRepository.save(usuario));
    }
}
