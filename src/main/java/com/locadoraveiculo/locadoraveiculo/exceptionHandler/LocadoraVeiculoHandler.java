package com.locadoraveiculo.locadoraveiculo.exceptionHandler;

import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.exception.UniqueProducerNameException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class LocadoraVeiculoHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
        String msgUser = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        String msgDev = ex.toString();
        List<Error> errors = Collections.singletonList(new Error(msgUser, msgDev));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({UniqueProducerNameException.class})
    public ResponseEntity<Object> handleUniqueProducerNameException(Exception ex, WebRequest request) {
        String msgUser = messageSource.getMessage("producer.unique.name", null, LocaleContextHolder.getLocale());
        String msgDev = ex.toString();
        List<Error> errors = Collections.singletonList(new Error(msgUser, msgDev));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
        String msgUser = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        String msgDev = ex.toString();
        List<Error> errors = Collections.singletonList(new Error(msgUser, msgDev));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Getter
    public static class Error {
        private String msgUser;
        private String msgDev;

        public Error(String msgUser, String msgDev) {
            super();
            this.msgUser = msgUser;
            this.msgDev = msgDev;
        }
    }
}
