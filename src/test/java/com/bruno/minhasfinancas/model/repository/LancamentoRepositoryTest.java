package com.bruno.minhasfinancas.model.repository;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bruno.minhasfinancas.enums.StatusLancamento;
import com.bruno.minhasfinancas.model.entity.Lancamento;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	LancamentoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void salvarLancamento() {
		Lancamento lancamento = criarLancamentoTest();
		
	lancamento =repository.save(lancamento);
	Assertions.assertThat(lancamento.getId()).isNotNull();
	
	}
	
	@Test
	public void deletarLancamento() {
		Lancamento lancamento = criarEPersistirLancamento();
		lancamento = entityManager.find(Lancamento.class, lancamento.getId());

		repository.delete(lancamento);
		
		Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
		Assertions.assertThat(lancamento.getId()).isNotEqualTo(1L);
		
	}
	@Test
	public void atualizarLancamento() {
		Lancamento lancamento = criarEPersistirLancamento();
		lancamento.setAno(2020);
		lancamento.setDescricao("atualizando");
		lancamento.setStatus(StatusLancamento.CANCELADO);
		
		repository.save(lancamento);
		Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
		Assertions.assertThat(lancamento.getAno()).isEqualTo(2020);
		Assertions.assertThat(lancamento.getDescricao()).isEqualTo("atualizando");
		Assertions.assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.CANCELADO);
		
		
	}

	private Lancamento criarEPersistirLancamento() {
		Lancamento lancamento = criarLancamentoTest();
		entityManager.persist(lancamento);
		return lancamento;
	}
	
	
	

	public static Lancamento criarLancamentoTest() {
		return Lancamento.builder()
				.ano(2021)
				.mes(1)
				.descricao("Lancamento qualquer")
				.valor(BigDecimal.TEN)
				.status(StatusLancamento.PENDENTE)
				.dataCadastro(Calendar.getInstance())
				.build();
		
	}
	
	
	
}
