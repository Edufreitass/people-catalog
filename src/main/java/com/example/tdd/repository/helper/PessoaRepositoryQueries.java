package com.example.tdd.repository.helper;

import java.util.List;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.repository.filtro.PessoaFiltro;

public interface PessoaRepositoryQueries {

	List<Pessoa> filtrar(PessoaFiltro filtro);
	
}
