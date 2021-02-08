package com.bruno.minhasfinancas.controller;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bruno.minhasfinancas.dto.UsuarioDTO;
import com.bruno.minhasfinancas.model.entity.Usuario;
import com.bruno.minhasfinancas.model.repository.UsuarioRepository;
import com.bruno.minhasfinancas.service.LancamentoService;
import com.bruno.minhasfinancas.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	static final String API ="/api/usuario";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	UsuarioService service;
	
	@MockBean
	UsuarioRepository repository;
	
	@MockBean
	LancamentoService lancamentoService;
	
	@Test
	public void autenticarUsuario() throws Exception {
		
		String email ="usuario@email.com";
		String senha ="123";
		UsuarioDTO dto = UsuarioDTO.builder()
				.email(email)
				.senha(senha).build();
		
		Usuario usuario = Usuario.builder().id(1L)
				.email(email)
				.senha(senha)
				.build();
		
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		Mockito.when(service.autenticar(email, senha)).thenReturn(usuario);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		
	MockHttpServletRequestBuilder request=MockMvcRequestBuilders.post(API.concat("autenticar"))
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON).content(json);
	
	mvc.perform(request)
	.andExpect(MockMvcResultMatchers.status().isOk())
	.andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
	.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
	.andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
	}
}
