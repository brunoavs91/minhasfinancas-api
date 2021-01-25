package com.bruno.minhasfinancas.service;

import com.bruno.minhasfinancas.model.entity.Usuario;


public interface UsuarioService {
	
	/**
	 * 
	 * @param email
	 * @param senha
	 * @return
	 */
	Usuario autenticar(String email, String senha);
	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	Usuario salvar(Usuario usuario);
	
	/**
	 * 
	 * @param email
	 */
			
	void validarEmail(String email);
}
