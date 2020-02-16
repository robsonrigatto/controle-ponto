package br.com.robsonrigatto.controleponto.handler;

import br.com.robsonrigatto.controleponto.dto.ErrorDTO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleClientErrorException(HttpClientErrorException ex) {
        return new ResponseEntity<>(new ErrorDTO(ex.getStatusCode().value(), ex.getMessage()), ex.getStatusCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handlerConstraintViolationException(ConstraintViolationException ex) {
        String constraintName = ex.getConstraintName().split(":")[0].substring(1);
        String message = messageSource.getMessage(constraintName, new Object[0], Locale.getDefault());
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), message, ExceptionUtils.getStackTrace(ex)), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
