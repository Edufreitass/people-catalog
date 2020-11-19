package com.example.tdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tdd.modelo.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	Optional<Pessoa> findByCpf(String cpf);

	Optional<Pessoa> findByTelefoneAndTelefoneNumero(String ddd, String numero);

}
