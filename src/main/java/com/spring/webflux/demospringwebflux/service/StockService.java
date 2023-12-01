package com.spring.webflux.demospringwebflux.service;

import com.spring.webflux.demospringwebflux.dto.StockRequest;
import com.spring.webflux.demospringwebflux.dto.StockResponse;
import com.spring.webflux.demospringwebflux.exception.StockCreationException;
import com.spring.webflux.demospringwebflux.exception.StockNotFoundException;
import com.spring.webflux.demospringwebflux.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
@Slf4j
public class StockService {
    private StockRepository stockRepository;

    public Mono<StockResponse> getOneStock(String id) {
        return stockRepository.findById(id)
                .map(StockResponse::fromModel)
                .switchIfEmpty(Mono.error(
                        new StockNotFoundException(
                                "StockNotFound with id :" + id)))
                .doFirst(() -> log.info("retrieving stock with id: {}", id))
                .doOnNext(stock -> log.info("Stock found: {}", stock))
                .doOnError(ex -> log.error("Something went wrong while retrieving a stock. Error message: {}",ex.getMessage()))
                .doOnTerminate(() -> log.info("Finalized retrieving stock"))
                .doFinally(signalType -> log.info("finalized retrieving stock with signal type: {}", signalType));
    }

    public Flux<StockResponse> getAllStocks(BigDecimal priceGreaterThan) {
        return stockRepository
                .findAll()
                .filter(stock -> stock.getPrice().compareTo(priceGreaterThan) > 0)
                .map(StockResponse::fromModel)
                .doFirst(() -> log.info("retrieving stocks"))
                .doOnNext(stock -> log.info("Stock found: {}", stock))
                .doOnError(ex -> log.error("Something went wrong while retrieving a stock. Error message: {}",ex.getMessage()))
                .doOnTerminate(() -> log.info("Finalized retrieving stock"))
                .doFinally(signalType -> log.info("finalized retrieving stock with signal type: {}", signalType));
    }

    public Mono<StockResponse> createStock(StockRequest stockRequest) {
        return Mono.just(stockRequest)
                .map(StockRequest::toModel)
                .flatMap(stock -> stockRepository.save(stock)) // Similar to map but it has a double effect but also by also unwrap Mono or Flux from its arguments, if we had used a map instead we would have had a Mono<Mono> which is not right!
                .map(StockResponse::fromModel)
                .onErrorMap(ex -> new StockCreationException(ex.getMessage()));
    }
}
