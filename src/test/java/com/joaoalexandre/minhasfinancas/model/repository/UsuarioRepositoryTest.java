package com.joaoalexandre.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.joaoalexandre.minhasfinancas.model.entity.Usuario;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
     UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveraVerificarAExistenciaDeumEmail() {
		// cenário
	Usuario usuario = criarUsuario();
		
		// ação / execucao
	 boolean result = repository.existsByEmail("usuario@email.com");
		
		// verificacao
		
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		// cenário
		
		
		//acao
		boolean result = repository.existsByEmail("usuario@email.com");
		
		//verificacão
		Assertions.assertThat(result).isFalse();
		
	}
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		//Cenario
		Usuario usuario = criarUsuario();
		
	//ação 
	Usuario usuarioSalvo =	repository.save(usuario);
	
	// Verificação
	Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	
		
	}
	
	@Test
	public void deveBuscarUsuarioPorEmail() {
		
		//Cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		
		// Verificação
	Optional<Usuario> result =	repository.findByEmail("usuario@email.com");
	
	Assertions.assertThat(result.isPresent()).isTrue();
		
		
	}
	
	@Test
	public void deveRetornarVazioAoUsuarioPorEmailQuandoNãoExisteNabase() {
		
		//Cenario
		
		
		
		
		// Verificação
	Optional<Usuario> result =	repository.findByEmail("usuario@email.com");
	
	Assertions.assertThat(result.isPresent()).isFalse();
		
		
	}
	
	public static Usuario criarUsuario() {
		return 
				Usuario.
				builder().
				nome("usuario")
				.email("usuario@email.com")
				.senha("senha")
				.build();		
	}

}
