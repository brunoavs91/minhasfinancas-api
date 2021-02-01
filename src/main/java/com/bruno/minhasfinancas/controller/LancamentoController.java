package com.bruno.minhasfinancas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.minhasfinancas.converter.LancamentoConverter;
import com.bruno.minhasfinancas.dto.AtualizaStatusDTO;
import com.bruno.minhasfinancas.dto.LancamentoDTO;
import com.bruno.minhasfinancas.enums.StatusLancamento;
import com.bruno.minhasfinancas.model.entity.Lancamento;
import com.bruno.minhasfinancas.model.entity.Usuario;
import com.bruno.minhasfinancas.service.LancamentoService;
import com.bruno.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

	@Autowired
	private LancamentoService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LancamentoConverter converter;
	
	@PostMapping
	public ResponseEntity salvar (@RequestBody LancamentoDTO dto) {
		try {

			Lancamento lancamento = converter.converterToEntity(dto);
			service.salvar(lancamento);
			return ResponseEntity.ok(lancamento);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity atualizar(@PathVariable Long id, @RequestBody LancamentoDTO dto) {

		try {
			Lancamento lancamento = service.atualizarLancamento(id, dto);
			return ResponseEntity.ok(lancamento);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity deletar (@PathVariable Long id) {
		
		try {
			Lancamento lancamento = service.obterPorId(id);
			service.deletar(lancamento);
			return ResponseEntity.noContent().build();
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "usuario")  Long idUsuario) {
		
		try {
			Lancamento lancamentoFiltro = new Lancamento();
			lancamentoFiltro.setDescricao(descricao);
			lancamentoFiltro.setMes(mes);
			lancamentoFiltro.setAno(ano);
			
			Usuario usuario = usuarioService.obterPorId(idUsuario);
			lancamentoFiltro.setUsuario(usuario);
			
			List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
			return ResponseEntity.ok(lancamentos);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		

	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus(@PathVariable Long id, @RequestBody AtualizaStatusDTO dto) {

		try {

			StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
			if (statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Status invalido");
			}
			Lancamento lancamento = service.obterPorId(id);
			lancamento.setStatus(statusSelecionado);
			service.atualizar(lancamento);
			return ResponseEntity.ok(lancamento);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
}
