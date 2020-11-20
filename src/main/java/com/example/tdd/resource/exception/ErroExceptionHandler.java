package com.example.tdd.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.tdd.servico.exception.TelefoneNaoEncontradoException;

@RestControllerAdvice
public class ErroExceptionHandler {

	@ExceptionHandler({TelefoneNaoEncontradoException.class})
	public ResponseEntity<Erro> handleTelefoneNaoEncontradoException(TelefoneNaoEncontradoException e) {
		return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.NOT_FOUND);
	}
	
}
