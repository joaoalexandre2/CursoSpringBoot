package com.joaoalexandre.minhasfinancas.service;

import static org.mockito.ArgumentMatchers.isNotNull;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.joaoalexandre.minhasfinancas.exception.RegraNegocioException;
import com.joaoalexandre.minhasfinancas.model.entity.Usuario;
import com.joaoalexandre.minhasfinancas.model.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	

	@Test(expected = Test.None.class)
	public void DeveSalvarUmUsuario() {
		// Cenario
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder()
				.id(1l)
				.nome("nome")
				.email("email@email.com")
				.senha("senha").build();
		
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		// Ação
		Usuario usuaarioSalvo = service.salvarUsuario(new Usuario());
		
		
		// Verificação
		Assertions.assertThat(usuaarioSalvo).isNotNull();
		Assertions.assertThat(usuaarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuaarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuaarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuaarioSalvo.getSenha()).isEqualTo("senha");
	}
	
	@Test(expected = RegraNegocioException.class )
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		//Cenario	
		String email = "email@email.com";
		Usuario usuario = Usuario.builder()
				.email(email).build();
		
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		//Ação
		service.salvarUsuario(usuario);
		
		// Verificação
		Mockito.verify(repository, Mockito.never()).save(usuario);
		
		
	}
	
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		//Cenario
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//ação
		Usuario result = service.autenticar(email, senha);
		
		// Verificação
		Assertions.assertThat(result).isNotNull();
		
	}
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		// Cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
	
		
		//Ação
		service.validarEmail("email@email.com");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarQuandoExistirEmailCadastrado() {
		
		//Cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		//Ação
		service.validarEmail("email@email.com");
		
		// Ação
		
	}

}
