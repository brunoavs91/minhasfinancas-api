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

import com.bruno.minhasfinancas.dto.LancamentoDTO;
import com.bruno.minhasfinancas.enums.StatusLancamento;
import com.bruno.minhasfinancas.enums.TipoLancamento;
import com.bruno.minhasfinancas.exception.BusinessExcepetion;
import com.bruno.minhasfinancas.exception.ObjectNotFoundException;
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
		validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
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

	@Override
	public Lancamento obterPorId(Long id) {

		return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Lancamento nao foi encontrado"));
	}

	@Override
	public Lancamento atualizarLancamento(Long id, LancamentoDTO dto) {
		Lancamento lancamento = obterPorId(id);
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		lancamento.setValor(dto.getValor());
		return lancamento;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal obterSaldoPorUsuario(Long id) {
	BigDecimal receitas = repository.obterSaldoPorTipoLancamentoUsuario(id, TipoLancamento.RECEITA);
	BigDecimal despesas = repository.obterSaldoPorTipoLancamentoUsuario(id, TipoLancamento.DESPESA);
	
	
	
	if(receitas == null) {
		receitas = BigDecimal.ZERO;
	}
	if(despesas == null) {
		despesas = BigDecimal.ZERO;
	}
		return receitas.subtract(despesas);
	}

}
