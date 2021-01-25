package com.bruno.minhasfinancas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.minhasfinancas.dto.UsuarioDTO;
import com.bruno.minhasfinancas.model.entity.Usuario;
import com.bruno.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		
		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha()).build();
		try {
			Usuario usuarioSalvo = usuarioService.salvar(usuario);
			return ResponseEntity.status(HttpStatus.OK).body(usuarioSalvo);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar (@RequestBody UsuarioDTO dto) {
		try {
			
			Usuario usuarioAutenticado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
			
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
}
