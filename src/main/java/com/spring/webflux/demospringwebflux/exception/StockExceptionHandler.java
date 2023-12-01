package com.spring.webflux.demospringwebflux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockExceptionHandler {

    @ExceptionHandler(StockNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorMessage handlerControllerNotFoundException(StockNotFoundException exception) {
        return ErrorMessage.builder()
                .message(exception.getMessage()).build();
    }

    @ExceptionHandler(StockCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleStockCreationException(StockCreationException ex) {
        return ErrorMessage.builder().message(ex.getMessage()).build();
    }
}
