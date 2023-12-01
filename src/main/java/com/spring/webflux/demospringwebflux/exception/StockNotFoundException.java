package com.spring.webflux.demospringwebflux.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockNotFoundException extends RuntimeException {
    private String message;
}
