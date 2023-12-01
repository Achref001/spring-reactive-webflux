package com.spring.webflux.demospringwebflux.controller;

import com.spring.webflux.demospringwebflux.dto.StockRequest;
import com.spring.webflux.demospringwebflux.dto.StockResponse;
import com.spring.webflux.demospringwebflux.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/stocks")
@AllArgsConstructor
public class StockController {

    private StockService stockService;

    @GetMapping("/{id}")
    public Mono<StockResponse> getOneStock(@PathVariable String id) {
        return stockService.getOneStock(id);
    }

    @GetMapping
    public Flux<StockResponse> getAllStocks(@RequestParam(required = false, defaultValue = "0")BigDecimal priceGreaterThan) {
        return stockService.getAllStocks(priceGreaterThan);
    }

    @PostMapping
    public Mono<StockResponse> createStock(@RequestBody StockRequest stockRequest) {
        return stockService.createStock(stockRequest);
    }
}
