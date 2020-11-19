package com.example.tdd.modelo;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {

	private String nome;
	private String cpf;
	private List<Endereco> enderecos = new ArrayList<>();
	private List<Telefone> telefones = new ArrayList<>();

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

	public void adicionaEndereco(Endereco endereco) {
		enderecos.add(endereco);
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void adicionaTelefone(Telefone telefone) {
		telefones.add(telefone);
	}

}
