package com.example.tdd.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.example.tdd.PeopleCatalogApplicationTests;
import com.example.tdd.modelo.Pessoa;
import com.example.tdd.modelo.Telefone;

import io.restassured.http.ContentType;

public class PessoaResourceTest extends PeopleCatalogApplicationTests {

	@Test
	void deveProcurarPessoaPeloDddENumeroDoTelefone() throws Exception {
		given()
				.pathParam("ddd", "86")
				.pathParam("numero", "35006330")
		.get("/pessoas/{ddd}/{numero}")
		.then()
				.log().body().and()
				.statusCode(HttpStatus.OK.value())
				.body("codigo", equalTo(3),
						"nome", equalTo("Cauê"),
						"cpf", equalTo("38767897100"));
	}
	
	@Test
	void deveRetornarErroNaoEncontradoQuandoBuscarPessoaPorTelefoneInexistente() throws Exception {
		given()
				.pathParam("ddd", "99")
				.pathParam("numero", "987654321")
		.get("/pessoas/{ddd}/{numero}")
		.then()
				.log().body().and()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.body("message", equalTo("Não existe pessoa com o telefone (99)987654321"));
	}
	
	@Test
	void deveSalvarNovaPessoaNoSistema() throws Exception {
		final Pessoa pessoa = new Pessoa();
		pessoa.setNome("Fernanda");
		pessoa.setCpf("27161503469");
		
		final Telefone telefone = new Telefone();
		telefone.setDdd("67");
		telefone.setNumero("37132848");
		
		pessoa.adicionaTelefone(telefone);
		
		given()
				.request()
				.header("Accept", ContentType.ANY)
				.header("Content-type", ContentType.JSON)
				.body(pessoa)
		.when()
		.post("/pessoas")
		.then()
				.log().headers().and()
				.log().body().and()
				.statusCode(HttpStatus.CREATED.value())
				.header("Location", equalTo("http://localhost:" + porta + "/pessoas/67/37132848"))
				.body("codigo", equalTo(6),
						"nome", equalTo("Fernanda"),
						"cpf", equalTo("27161503469"));
		
	}
	
	@Test
	void naoDeveSalvarDuasPessoasComOMesmoCPF() throws Exception {
		final Pessoa pessoa = new Pessoa();
		pessoa.setNome("Fernanda");
		pessoa.setCpf("72788740417");
		
		final Telefone telefone = new Telefone();
		telefone.setDdd("67");
		telefone.setNumero("37132848");
		
		pessoa.adicionaTelefone(telefone);
		
		given()
				.request()
				.header("Accept", ContentType.ANY)
				.header("Content-type", ContentType.JSON)
				.body(pessoa)
		.when()
		.post("/pessoas")
		.then()
				.log().body().and()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("message", equalTo("Já existe pessoa cadastrada com o CPF '72788740417'"));
	}
	
}
