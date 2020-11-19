package com.example.tdd.repository;

import java.util.Optional;

import com.example.tdd.modelo.Pessoa;

public interface PessoaRepository {

	Pessoa save(Pessoa pessoa);

	Optional<Pessoa> findByCpf(String cpf);

}
