package com.example.tdd.servico.impl;

import java.util.Optional;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.modelo.Telefone;
import com.example.tdd.repository.PessoaRepository;
import com.example.tdd.servico.PessoaService;
import com.example.tdd.servico.exception.TelefoneNaoEncotradoException;
import com.example.tdd.servico.exception.UnicidadeCpfException;
import com.example.tdd.servico.exception.UnicidadeTelefoneException;

public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository pessoaRepository;

	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException {
		Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());

		if (optional.isPresent()) {
			throw new UnicidadeCpfException();
		}

		String ddd = pessoa.getTelefones().get(0).getDdd();
		String numero = pessoa.getTelefones().get(0).getNumero();
		optional = pessoaRepository.findByTelefoneAndTelefoneNumero(ddd, numero);

		if (optional.isPresent()) {
			throw new UnicidadeTelefoneException();
		}

		return pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncotradoException {
		Optional<Pessoa> optional = pessoaRepository.findByTelefoneAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		return optional.orElseThrow(TelefoneNaoEncotradoException::new);
	}

}
