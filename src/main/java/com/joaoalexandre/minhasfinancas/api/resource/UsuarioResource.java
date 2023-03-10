package com.joaoalexandre.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaoalexandre.minhasfinancas.api.dto.UsuarioDto;
import com.joaoalexandre.minhasfinancas.exception.ErroAutenticacao;
import com.joaoalexandre.minhasfinancas.exception.RegraNegocioException;
import com.joaoalexandre.minhasfinancas.model.entity.Usuario;
import com.joaoalexandre.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
	
	private UsuarioService service;
	
	public UsuarioResource(UsuarioService service ) {
		this.service = service;
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDto dto) {
		try {
		Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
		return ResponseEntity.ok(usuarioAutenticado);
		}catch(ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PostMapping()
	public ResponseEntity salvar(@RequestBody UsuarioDto dto) {
		
		Usuario usuario = Usuario.builder().nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha()).build();
		
		try {
		Usuario UsuarioSalvo =	service.salvarUsuario(usuario);
		return new ResponseEntity(UsuarioSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	

}
