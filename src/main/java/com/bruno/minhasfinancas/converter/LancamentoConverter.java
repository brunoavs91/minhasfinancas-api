package com.bruno.minhasfinancas.converter;

import java.util.Calendar;

import org.apache.logging.log4j.util.Timer.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bruno.minhasfinancas.dto.LancamentoDTO;
import com.bruno.minhasfinancas.enums.StatusLancamento;
import com.bruno.minhasfinancas.enums.TipoLancamento;
import com.bruno.minhasfinancas.model.entity.Lancamento;
import com.bruno.minhasfinancas.model.entity.Usuario;
import com.bruno.minhasfinancas.service.UsuarioService;

@Component
public class LancamentoConverter {
	
	@Autowired
	private UsuarioService  usuarioService;

	public Lancamento converterToEntity(LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		lancamento.setId(dto.getId());
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setDataCadastro(dto.getDataCadastro() != null? dto.getDataCadastro():Calendar.getInstance());
		lancamento.setValor(dto.getValor());
		
		Usuario usuario = usuarioService
				.obterPorId(dto.getUsuario());
		lancamento.setUsuario(usuario);
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		
		return lancamento;
	}
	
}
