package com.bruno.minhasfinancas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.minhasfinancas.enums.StatusLancamento;
import com.bruno.minhasfinancas.exception.BusinessExcepetion;
import com.bruno.minhasfinancas.model.entity.Lancamento;
import com.bruno.minhasfinancas.model.repository.LancamentoRepository;
import com.bruno.minhasfinancas.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService{
	
	@Autowired
	LancamentoRepository repository;

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		
		Example example = Example.of(lancamentoFiltro,
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
	
		lancamento.setStatus(status);
		atualizar(lancamento);
	}

	@Override
	public void validar(Lancamento lancamento) {
		if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
			throw new BusinessExcepetion("Informe uma descriçao válida");
		}
		if(lancamento.getMes() == null || lancamento.getMes() < 1 
				|| lancamento.getMes() > 12) {
			throw new BusinessExcepetion("Informe um mês válido");
		}
		if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4 ) {
			throw new BusinessExcepetion("Informe um ano válida");
			
		}
		if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
			throw new BusinessExcepetion("Informe um Usuário");
			
		}
		if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new BusinessExcepetion("informe um valor válido");
		
		}
		if(lancamento.getTipo() == null) {
			throw new BusinessExcepetion("Informe um tipo de Lançamento.");
		}
	}

}