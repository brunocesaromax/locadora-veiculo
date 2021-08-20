package com.locadoraveiculo.locadoraveiculo.exceptionHandler;

import com.locadoraveiculo.locadoraveiculo.exception.CarModelException;
import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.exception.UniqueProducerNameException;
import com.locadoraveiculo.locadoraveiculo.service.LocaleMessageSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class LocadoraVeiculoHandler extends ResponseEntityExceptionHandler {

    private final LocaleMessageSource localeMessageSource;

    @ExceptionHandler({IllegalArgumentException.class, NoResultException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
        String msgUser = localeMessageSource.getMessage("resource.not-found");
        String msgDev = ex.toString();
        List<Error> errors = Collections.singletonList(new Error(msgUser, msgDev));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> errors = getErrorsList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(Exception ex, WebRequest request) {
        String msgUser = localeMessageSource.getMessage("fail.convert.types");
        String msgDev = ex.toString();
        List<Error> errors = Collections.singletonList(new Error(msgUser, msgDev));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({UniqueProducerNameException.class})
    public ResponseEntity<Object> handleUniqueProducerNameException(Exception ex, WebRequest request) {
        String msgUser = localeMessageSource.getMessage("Producer.unique.name");
        String msgDev = ex.toString();
        List<Error> errors = Collections.singletonList(new Error(msgUser, msgDev));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
        String msgUser = localeMessageSource.getMessage("resource.not-found");
        String msgDev = ex.toString();
        List<Error> errors = Collections.singletonList(new Error(msgUser, msgDev));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({CarModelException.class})
    public ResponseEntity<Object> handleCarModelException(Exception ex, WebRequest request) {
        String msgUser = ex.getMessage();
        String msgDev = ex.toString();
        List<Error> errors = Collections.singletonList(new Error(msgUser, msgDev));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> getErrorsList(BindingResult bindingResult) {

        List<Error> errors = new ArrayList<>();

//		Retornar todos os errors nos campos do objeto
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msgUser = localeMessageSource.getMessage(fieldError);
            String msgDev = fieldError.toString();
            errors.add(new Error(msgUser, msgDev));
        }

        return errors;

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
