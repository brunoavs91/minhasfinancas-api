package com.bruno.minhasfinancas.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.minhasfinancas.exception.AutenticacaoException;
import com.bruno.minhasfinancas.exception.BusinessExcepetion;
import com.bruno.minhasfinancas.exception.ObjectNotFoundException;
import com.bruno.minhasfinancas.model.entity.Usuario;
import com.bruno.minhasfinancas.model.repository.UsuarioRepository;
import com.bruno.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public Usuario autenticar(String email, String senha) {
	
		Usuario usuario = repository.findByEmail(email)
				.orElseThrow(()-> new  ObjectNotFoundException("usuario nao encontrado"));
		
		if(!usuario.getSenha().equals(senha)) {
			throw new AutenticacaoException("senha invalida");
		}
		return usuario;
	}

	@Override
	@Transactional
	public Usuario salvar(Usuario usuario) {

		validarEmail(usuario.getEmail());

		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		
		boolean existe = repository.existsByEmail(email);
		
		if(existe) {
			throw new BusinessExcepetion("Ja existe um usuario cadastrado com este email");
		}
	}

}
