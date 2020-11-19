package com.example.tdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tdd.modelo.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	Optional<Pessoa> findByCpf(String cpf);

	// TODO Query temporaria, será removida futuramente
	@Query("SELECT bean FROM Pessoa bean WHERE 1=1")
	Optional<Pessoa> findByTelefoneAndTelefoneNumero(String ddd, String numero);

}
