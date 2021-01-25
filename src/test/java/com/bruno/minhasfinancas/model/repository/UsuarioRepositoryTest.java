package com.bruno.minhasfinancas.model.repository;

import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bruno.minhasfinancas.model.entity.Usuario;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TestEntityManager entityManager;

	@Test
	public void contemExistenciaEmail() {
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		boolean result =usuarioRepository.existsByEmail("usuario@email.com");
		
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void naoContemExistenciaEmail() {
		
		boolean result = usuarioRepository.existsByEmail("usuario@email.com");
		
		Assertions.assertThat(result).isFalse();
		
	}
	
	@Test
	public void persistirUsuario() {
		
		Usuario usuario = criarUsuario();
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void buscarUsuarioPorEmail() {
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
	Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	public static Usuario criarUsuario() {
		return Usuario.builder()
				.nome("usuario")
				.email("usuario@email.com")
				.senha("senha")
				.build();
	}
}
