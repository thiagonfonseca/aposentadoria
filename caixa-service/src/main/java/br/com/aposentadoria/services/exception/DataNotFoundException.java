package br.com.aposentadoria.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends Exception {
    public DataNotFoundException(Long id) {
        super("Aporte " + id + " não encontrado!");
    }
    public DataNotFoundException(String cpf) {
        super("Beneficiario " + cpf + " não encontrado!");
    }
}
