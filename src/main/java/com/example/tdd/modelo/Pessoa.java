package com.example.tdd.modelo;

import java.util.List;

public class Pessoa {

	private String nome;
	private String cpf;
	private List<Endereco> enderecos;
	private List<Telefone> telefones;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

}
