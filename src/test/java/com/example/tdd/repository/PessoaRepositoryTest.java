package com.example.tdd.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.repository.filtro.PessoaFiltro;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

	@Autowired
	private PessoaRepository sut;

	@Test
	void deveProcurarPessoaPeloCPF() throws Exception {
		Optional<Pessoa> optional = sut.findByCpf("38767897100");

		assertThat(optional.isPresent()).isTrue();

		Pessoa pessoa = optional.get();
		assertThat(pessoa.getCodigo()).isEqualTo(3L);
		assertThat(pessoa.getNome()).isEqualTo("Cauê");
		assertThat(pessoa.getCpf()).isEqualTo("38767897100");
	}

	@Test
	void naoDeveEncontrarPessoaDeCPFInexistente() throws Exception {
		Optional<Pessoa> optional = sut.findByCpf("85165164681");

		assertThat(optional.isPresent()).isFalse();
	}

	@Test
	void deveEncontrarPessoaPeloDddENumeroDeTelefone() throws Exception {
		Optional<Pessoa> optional = sut.findByTelefoneAndTelefoneNumero("86", "35006330");

		assertThat(optional.isPresent()).isTrue();

		Pessoa pessoa = optional.get();
		assertThat(pessoa.getCodigo()).isEqualTo(3L);
		assertThat(pessoa.getNome()).isEqualTo("Cauê");
		assertThat(pessoa.getCpf()).isEqualTo("38767897100");
	}

	@Test
	void naoDeveEncontrarPessoaCujoDddETelefoneNaoEstejamCadastrados() throws Exception {
		Optional<Pessoa> optional = sut.findByTelefoneAndTelefoneNumero("80", "22006330");

		assertThat(optional.isPresent()).isFalse();
	}

	@Test
	void deveFiltrarPessoasPorParteDoNome() throws Exception {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setNome("a");

		List<Pessoa> pessoas = sut.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(3);
	}

	@Test
	void deveFiltrarPessoasPorParteDoCPF() throws Exception {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setCpf("78");

		List<Pessoa> pessoas = sut.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(3);
	}

	@Test
	void deveFiltrarPessoasPorFiltroComposto() throws Exception {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setNome("a");
		filtro.setCpf("78");

		List<Pessoa> pessoas = sut.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(2);
	}

	@Test
	void deveFiltrarPessoasPeloDddDoTelefone() throws Exception {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setDdd("21");

		List<Pessoa> pessoas = sut.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(1);
	}

	@Test
	void deveFiltrarPessoasPeloNumeroDoTelefone() throws Exception {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setTelefone("997504");

		List<Pessoa> pessoas = sut.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(0);
	}

}
