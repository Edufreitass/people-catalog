package com.example.tdd.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.modelo.Telefone;
import com.example.tdd.servico.PessoaService;
import com.example.tdd.servico.exception.TelefoneNaoEncontradoException;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService service;

	@GetMapping("/{ddd}/{numero}")
	public ResponseEntity<Pessoa> buscarPorDddENumeroTelefone(@PathVariable String ddd, 
															  @PathVariable String numero) throws TelefoneNaoEncontradoException {
		final Telefone telefone = new Telefone();
		telefone.setDdd(ddd);
		telefone.setNumero(numero);

		final Pessoa pessoa = service.buscarPorTelefone(telefone);

		return new ResponseEntity<>(pessoa, HttpStatus.OK);
	}

}
