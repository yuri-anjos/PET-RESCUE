package br.com.petrescue.api.service;

import br.com.petrescue.api.controller.dto.CarteiraDTO;
import br.com.petrescue.api.controller.dto.UsuarioDTO;
import br.com.petrescue.api.controller.dto.VaquinhaDTO;
import br.com.petrescue.api.domain.Usuario;
import br.com.petrescue.api.domain.enums.TipoUsuario;
import br.com.petrescue.api.exceptions.NaoEncontradoException;
import br.com.petrescue.api.exceptions.NegocioException;
import br.com.petrescue.api.repository.UsuarioRepository;
import br.com.petrescue.api.utils.GeralValidator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GeralValidator geralValidator;

    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        this.geralValidator.string(usuario.getEmail(), "Email");
        this.geralValidator.string(usuario.getNome(), "Nome");
        this.geralValidator.string(usuario.getSenha(), "Senha");
        this.geralValidator.validarEmail(usuario.getEmail());
        if (TipoUsuario.INSTITUCIONAL.equals(usuario.getTipoUsuario())) {
            this.geralValidator.string(usuario.getDescricao(), "Descrição de Ong/Instituição");
            this.geralValidator.validarCpfCnpj(usuario.getCpfCnpj());
        }
        usuario.setSaldo(0.0);
        return new UsuarioDTO(this.usuarioRepository.save(usuario));
    }

    public UsuarioDTO editarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        this.geralValidator.string(usuario.getEmail(), "Email");
        this.geralValidator.string(usuario.getNome(), "Nome");
        this.geralValidator.string(usuario.getSenha(), "Senha");
        this.geralValidator.validarEmail(usuario.getEmail());
        if (TipoUsuario.INSTITUCIONAL.equals(usuario.getTipoUsuario())) {
            this.geralValidator.string(usuario.getDescricao(), "Descrição de ONG/instituição");
            this.geralValidator.validarCpfCnpj(usuario.getCpfCnpj());
        }
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
        if (carteiraDTO.getSaldoAdicional() == null || carteiraDTO.getSaldoAdicional() <= 0) {
            throw new NegocioException("Valor de depósito inválido!");
        }
        usuario.setSaldo(usuario.getSaldo() + carteiraDTO.getSaldoAdicional());
        return new UsuarioDTO(this.usuarioRepository.save(usuario));
    }

    public List<UsuarioDTO> buscarInstituicoes(Integer pg) {
        Pageable pageable = PageRequest.of(pg, 10);
        return this.usuarioRepository.findByTipoUsuario(TipoUsuario.INSTITUCIONAL, pageable).stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }
}
