package com.joaoalexandre.minhasfinancas.service;

import java.util.List;

import com.joaoalexandre.minhasfinancas.model.entity.Lancamento;
import com.joaoalexandre.minhasfinancas.model.enums.StatusLancamento;

public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
   List<Lancamento> buscar(Lancamento lancamentofiltro);
   
   void atualizarStatus(Lancamento lancamento, StatusLancamento status);
   
   void validar(Lancamento lancamento);
	

}
