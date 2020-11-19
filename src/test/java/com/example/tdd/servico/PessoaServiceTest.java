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
import com.example.tdd.servico.exception.TelefoneNaoEncotradoException;
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
	public void setUp() throws Exception {
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
	public void deveSalvarPessoaNoRepositorio() throws Exception {
		sut.salvar(pessoa);

		verify(pessoaRepository).save(pessoa);
	}

	@Test
	public void naoDeveSalvarDuasPessoasComOMesmoCPF() throws Exception {
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

		assertThrows(UnicidadeCpfException.class, () -> sut.salvar(pessoa));
	}

	@Test
	public void naoDeveSalvarDuasPessoaComOMesmoTelefone() throws Exception {
		when(pessoaRepository.findByTelefoneAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

		assertThrows(UnicidadeTelefoneException.class, () -> sut.salvar(pessoa));
	}

	@Test
	public void deveRetornarExcecaoDeNaoEncontradoQuandoNaoExistirPessoaComODddENumeroDeTelefone() throws Exception {
		assertThrows(TelefoneNaoEncotradoException.class, () -> sut.buscarPorTelefone(telefone));
	}

	@Test
	public void deveProcurarPessoaPeloDddENumeroDoTelefone() throws Exception {
		when(pessoaRepository.findByTelefoneAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

		Pessoa pessoaTeste = sut.buscarPorTelefone(telefone);

		verify(pessoaRepository).findByTelefoneAndTelefoneNumero(DDD, NUMERO);

		assertThat(pessoaTeste).isNotNull();
		assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
		assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
	}

}
