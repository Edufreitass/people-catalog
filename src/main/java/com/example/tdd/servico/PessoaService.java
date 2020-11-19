package com.example.tdd.servico;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.servico.exception.UnicidadeCpfException;

public interface PessoaService {

	Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException;

}
