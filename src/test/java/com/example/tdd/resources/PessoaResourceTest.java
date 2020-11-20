package com.example.tdd.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.example.tdd.PeopleCatalogApplicationTests;

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
	
}
