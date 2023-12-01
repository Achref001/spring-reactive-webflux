package com.spring.webflux.demospringwebflux.model;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {
    @Id
    private String id;
    private String name;
    @NonNull
    private BigDecimal price;
    private String currency;
}
