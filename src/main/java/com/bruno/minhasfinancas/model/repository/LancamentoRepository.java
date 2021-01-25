package com.bruno.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.minhasfinancas.model.entity.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
