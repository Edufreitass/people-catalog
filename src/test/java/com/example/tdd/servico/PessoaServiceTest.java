package com.example.tdd.servico;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.modelo.Telefone;
import com.example.tdd.repository.PessoaRepository;
import com.example.tdd.servico.exception.TelefoneNaoEncontradoException;
import com.example.tdd.servico.exception.UnicidadeCpfException;
import com.example.tdd.servico.exception.UnicidadeTelefoneException;
import com.example.tdd.servico.impl.PessoaServiceImpl;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

	private static final String NOME = "Eduardo Freitas";
	private static final String CPF = "1234567890";
	private static final String DDD = "55";
	private static final String NUMERO = "0987654321";

	@MockBean
	private PessoaRepository pessoaRepository;

	private PessoaService sut;

	private Pessoa pessoa;
	private Telefone telefone;

	@BeforeEach
	void setUp() throws Exception {
		sut = new PessoaServiceImpl(pessoaRepository);

		pessoa = new Pessoa();
		pessoa.setNome(NOME);
		pessoa.setCpf(CPF);

		telefone = new Telefone();
		telefone.setDdd(DDD);
		telefone.setNumero(NUMERO);

		pessoa.adicionaTelefone(telefone);
	}

	@Test
	void deveSalvarPessoaNoRepositorio() throws Exception {
		sut.salvar(pessoa);

		verify(pessoaRepository).save(pessoa);
	}

	@Test
	void naoDeveSalvarDuasPessoasComOMesmoCPF() throws Exception {
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

		UnicidadeCpfException e = assertThrows(UnicidadeCpfException.class, () -> sut.salvar(pessoa));
		assertEquals("Já existe pessoa cadastrada com o CPF '" + CPF + "'", e.getMessage());
	}

	@Test
	void naoDeveSalvarDuasPessoaComOMesmoTelefone() throws Exception {
		when(pessoaRepository.findByTelefoneAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

		assertThrows(UnicidadeTelefoneException.class, () -> sut.salvar(pessoa));
	}

	@Test
	void deveRetornarExcecaoDeNaoEncontradoQuandoNaoExistirPessoaComODddENumeroDeTelefone() throws Exception {
		assertThrows(TelefoneNaoEncontradoException.class, () -> sut.buscarPorTelefone(telefone));
	}
	
	@Test
	void deveRetornarDadosDoTelefoneDentroDaExcecaoDeTelefoneNaoEncontradoException() throws Exception {
		TelefoneNaoEncontradoException e = assertThrows(TelefoneNaoEncontradoException.class, () -> sut.buscarPorTelefone(telefone));
		assertEquals("Não existe pessoa com o telefone (" + DDD + ")" + NUMERO, e.getMessage());
	}

	@Test
	void deveProcurarPessoaPeloDddENumeroDoTelefone() throws Exception {
		when(pessoaRepository.findByTelefoneAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

		Pessoa pessoaTeste = sut.buscarPorTelefone(telefone);

		verify(pessoaRepository).findByTelefoneAndTelefoneNumero(DDD, NUMERO);

		assertThat(pessoaTeste).isNotNull();
		assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
		assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
	}

}
