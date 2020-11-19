package com.example.tdd.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.tdd.modelo.Pessoa;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

	@Autowired
	private PessoaRepository sut;

	@Test
	public void deveProcurarPessoaPeloCPF() throws Exception {
		Optional<Pessoa> optional = sut.findByCpf("38767897100");

		assertThat(optional.isPresent()).isTrue();

		Pessoa pessoa = optional.get();
		assertThat(pessoa.getCodigo()).isEqualTo(3L);
		assertThat(pessoa.getNome()).isEqualTo("Cauê");
		assertThat(pessoa.getCpf()).isEqualTo("38767897100");
	}

	@Test
	public void naoDeveEncontrarPessoaDeCPFInexistente() throws Exception {
		Optional<Pessoa> optional = sut.findByCpf("85165164681");

		assertThat(optional.isPresent()).isFalse();
	}

	@Test
	public void deveEncontrarPessoaPeloDddENumeroDeTelefone() throws Exception {
		Optional<Pessoa> optional = sut.findByTelefoneAndTelefoneNumero("86", "35006330");

		assertThat(optional.isPresent()).isTrue();

		Pessoa pessoa = optional.get();
		assertThat(pessoa.getCodigo()).isEqualTo(3L);
		assertThat(pessoa.getNome()).isEqualTo("Cauê");
		assertThat(pessoa.getCpf()).isEqualTo("38767897100");
	}

	@Test
	public void naoDeveEncontrarPessoaCujoDddETelefoneNaoEstejamCadastrados() throws Exception {
		Optional<Pessoa> optional = sut.findByTelefoneAndTelefoneNumero("80", "22006330");

		assertThat(optional.isPresent()).isFalse();
	}

}
