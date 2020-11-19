package com.example.tdd.servico.impl;

import java.util.Optional;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.repository.PessoaRepository;
import com.example.tdd.servico.PessoaService;
import com.example.tdd.servico.exception.UnicidadeCpfException;

public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository pessoaRepository;

	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException {
		Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());
		
		if(optional.isPresent()) {
			throw new UnicidadeCpfException();
		}
		
		return pessoaRepository.save(pessoa);
	}

}
