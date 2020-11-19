package com.example.tdd.servico;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.repository.PessoaRepository;
import com.example.tdd.servico.exception.UnicidadeCpfException;
import com.example.tdd.servico.impl.PessoaServiceImpl;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

	private static final String NOME = "Eduardo Freitas";
	private static final String CPF = "1234567890";

	@MockBean
	private PessoaRepository pessoaRepository;

	private PessoaService sut;

	private Pessoa pessoa;

	@BeforeEach
	public void setUp() throws Exception {
		sut = new PessoaServiceImpl(pessoaRepository);

		pessoa = new Pessoa();
		pessoa.setNome(NOME);
		pessoa.setCpf(CPF);

		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
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

}
