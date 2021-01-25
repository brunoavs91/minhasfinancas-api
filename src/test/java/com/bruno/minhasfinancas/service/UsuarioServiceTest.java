package com.bruno.minhasfinancas.service;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bruno.minhasfinancas.exception.AutenticacaoException;
import com.bruno.minhasfinancas.exception.BusinessExcepetion;
import com.bruno.minhasfinancas.exception.ObjectNotFoundException;
import com.bruno.minhasfinancas.model.entity.Usuario;
import com.bruno.minhasfinancas.model.repository.UsuarioRepository;
import com.bruno.minhasfinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@InjectMocks
	UsuarioServiceImpl usuarioService;
	
	@Mock
	UsuarioRepository usuarioRepository;
	
	String email ="email@email.com";
	
	
	@Before
	public void setUp() {
		System.out.println("Iniciando");
	}
	
	public void salvarUsuario() {
		Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
		
		Usuario usuario = Usuario.builder().id(1L)
				.nome("nome")
				.email("email@email.com")
				.senha("senha").build();
		Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		Usuario usuarioSalvo = usuarioService.salvar(new Usuario());
		
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1L);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		
		
	}
	
	public void naoSalvarUsuarioComEmailCadastrado(){
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(BusinessExcepetion.class).when(usuarioService).validarEmail(email);
		
		usuarioService.salvar(usuario);
		Mockito.verify(usuarioRepository,Mockito.never()).save(usuario);
		
		
	}
	
	@Test
	public void validarEmail() {
		
		when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
		usuarioService.validarEmail("email@email.com");
		//usuarioRepository.deleteAll();
		//assertThrows(org.junit.Test.None.class,()-> usuarioService.validarEmail("email@email.com"));
		
		//usuarioService.validarEmail("email@email.com");
	}
	
	@Test
	public void  existeEmailCadastrado() {
		//Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
		when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
		//usuarioService.validarEmail("email@email.com");
		
		assertThrows(BusinessExcepetion.class,()-> usuarioService.validarEmail("email@email.com"));
	}
	
	@Test
	public void autenticarUsuario() {
		String email = "email@email.com";
		String senha = "senha";
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
		when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

		Usuario result = usuarioService.autenticar(email, senha);

		Assertions.assertThat(result).isNotNull();
		assertDoesNotThrow(() -> usuarioService.autenticar(email, senha));
	}
	
	@Test
	public void validarUsuarioNaoEncontradoPorEmail() {
		when(usuarioRepository.findByEmail(Mockito.anyString()))
		.thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class,()-> usuarioService.autenticar("", ""));
	}
	@Test
	public void validarSenhaIncorreta() {
		when(usuarioRepository.findByEmail(Mockito.anyString()))
		.thenReturn(Optional.of(Usuario.builder().senha("").build()));
		assertThrows(AutenticacaoException.class,()-> usuarioService.autenticar("email@email.com", "senha"));
	}

}
