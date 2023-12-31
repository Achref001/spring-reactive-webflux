package com.spring.webflux.demospringwebflux.repository;

import com.spring.webflux.demospringwebflux.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, String> {
}
