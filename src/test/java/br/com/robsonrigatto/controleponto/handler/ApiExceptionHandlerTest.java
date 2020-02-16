package br.com.robsonrigatto.controleponto.handler;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApiExceptionHandlerTest {

    @Autowired
    private ApiExceptionHandler handler;

    @Test
    public void handleClientErrorExceptionTest() {
        ResponseEntity<Object> response = handler.handleClientErrorException(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "teste"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void handlerConstraintViolationExceptionTest() {
        ResponseEntity<Object> response = handler.handlerConstraintViolationException(new ConstraintViolationException("", new SQLException(), "\"FK_PONTO_ALUNO: "));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }
}
