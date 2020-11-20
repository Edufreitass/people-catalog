package com.example.tdd.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.modelo.Telefone;
import com.example.tdd.repository.PessoaRepository;
import com.example.tdd.repository.filtro.PessoaFiltro;
import com.example.tdd.servico.PessoaService;
import com.example.tdd.servico.exception.TelefoneNaoEncontradoException;
import com.example.tdd.servico.exception.UnicidadeCpfException;
import com.example.tdd.servico.exception.UnicidadeTelefoneException;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService service;
	
	@Autowired
	private PessoaRepository repository;

	@GetMapping("/{ddd}/{numero}")
	public ResponseEntity<Pessoa> buscarPorDddENumeroTelefone(@PathVariable String ddd, 
															  @PathVariable String numero) throws TelefoneNaoEncontradoException {
		final Telefone telefone = new Telefone();
		telefone.setDdd(ddd);
		telefone.setNumero(numero);

		final Pessoa pessoa = service.buscarPorTelefone(telefone);

		return new ResponseEntity<>(pessoa, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Pessoa> salvarNova(@RequestBody Pessoa pessoa, HttpServletResponse response) throws UnicidadeCpfException, UnicidadeTelefoneException {
		final Pessoa pessoaSalva = service.salvar(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{ddd}/{numero}")
				.buildAndExpand(pessoa.getTelefones().get(0).getDdd(), pessoa.getTelefones().get(0).getNumero()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return new ResponseEntity<>(pessoaSalva, HttpStatus.CREATED);
	}
	
	@PostMapping("/filtrar")
	public ResponseEntity<List<Pessoa>> filtrar(@RequestBody PessoaFiltro filtro) {
		final List<Pessoa> pessoas = repository.filtrar(filtro);
		return new ResponseEntity<>(pessoas, HttpStatus.OK);
	}
	
}
