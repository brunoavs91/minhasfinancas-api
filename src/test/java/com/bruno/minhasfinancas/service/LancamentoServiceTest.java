package com.bruno.minhasfinancas.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bruno.minhasfinancas.enums.StatusLancamento;
import com.bruno.minhasfinancas.model.entity.Lancamento;
import com.bruno.minhasfinancas.model.repository.LancamentoRepository;
import com.bruno.minhasfinancas.model.repository.LancamentoRepositoryTest;
import com.bruno.minhasfinancas.service.impl.LancamentoServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@SpyBean
	LancamentoServiceImpl service;
	
	@Mock
	LancamentoRepository repository;
	
	
	@Test
	public void salvarLancamento() {
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamentoTest();
		Mockito.doNothing().when(service).validar(lancamento);
		
		Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamentoTest();
		lancamentoSalvo.setId(1L);
		lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
		Mockito.when(repository.save(lancamento)).thenReturn(lancamentoSalvo);
		
		Lancamento lancamentoTest = service.salvar(lancamentoSalvo);
		
		Assertions.assertThat(lancamentoTest.getId()).isEqualTo(lancamentoSalvo.getId());
		Assertions.assertThat(lancamentoTest.getStatus()).isEqualTo(StatusLancamento.PENDENTE);
		
		
		service.salvar(lancamento);
		
	}
	public void salvarLancamentoErroValidacao() {
		
	}
//	public void deveFiltrarLancamento() {
//		Lancamento lancamento = LancamentoRepositoryTest.criarLancamentoTest();
//		lancamento.setId(1L);
//		List<Object> lista = Arrays.asList(lancamento);
//		List<Lancamento> resultado = service.buscar(lancamento);
//		
//		Assertions
//		.assertThat(lista)
//		.isNotEmpty()
//		.hasSize(1);
//	}
}
