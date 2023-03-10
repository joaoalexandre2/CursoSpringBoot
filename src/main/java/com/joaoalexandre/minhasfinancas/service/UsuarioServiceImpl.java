package com.joaoalexandre.minhasfinancas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.joaoalexandre.minhasfinancas.exception.ErroAutenticacao;
import com.joaoalexandre.minhasfinancas.exception.RegraNegocioException;
import com.joaoalexandre.minhasfinancas.model.entity.Usuario;
import com.joaoalexandre.minhasfinancas.model.repository.UsuarioRepository;

import jakarta.transaction.Transactional;


@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioRepository repository;
	
	
    @Autowired
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
	   Optional<Usuario> usuario = repository.findByEmail(email);
	
	if(!usuario.isPresent()) {
		throw new ErroAutenticacao("Usuário não encontrado para o e-mail informado.");
	}
	
	if(!usuario.get().getSenha().equals(senha)) {
		throw new ErroAutenticacao("Senha Invalida");
	}
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
	boolean existe = repository.existsByEmail(email);
	if(existe) {
		throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail");
	}
		
		
	}

}
