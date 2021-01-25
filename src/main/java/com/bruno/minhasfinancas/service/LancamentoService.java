package com.bruno.minhasfinancas.service;

import java.util.List;

import com.bruno.minhasfinancas.enums.StatusLancamento;
import com.bruno.minhasfinancas.model.entity.Lancamento;

public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento);
	
	void deletar (Lancamento lancamento);
	
	List<Lancamento> buscar (Lancamento lancamentoFiltro);
	
	void atualizarStatus (Lancamento lancamento, StatusLancamento status);
	
	void validar(Lancamento lancamento);

}
