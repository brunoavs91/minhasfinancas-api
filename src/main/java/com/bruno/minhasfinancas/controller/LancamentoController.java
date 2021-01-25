package com.bruno.minhasfinancas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.minhasfinancas.converter.LancamentoConverter;
import com.bruno.minhasfinancas.dto.LancamentoDTO;
import com.bruno.minhasfinancas.model.entity.Lancamento;
import com.bruno.minhasfinancas.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

	@Autowired
	private LancamentoService service;
	
	@Autowired
	private LancamentoConverter converter;
	
	@PostMapping
	public ResponseEntity salvar (@RequestBody LancamentoDTO dto) {
	Lancamento lancamento =	converter.converterToEntity(dto);
	service.salvar(lancamento);
	}
	
}
