package com.spring.webflux.demospringwebflux.service;

import com.spring.webflux.demospringwebflux.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private WebClient webClient;

    public UserService(@Value("${clients.github.api.url}") String githubUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(githubUrl)
                // add random header to a request
                // ExchangeFilterFunction is always requiring a Mono
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        clientRequest -> Mono.just(ClientRequest.from(clientRequest)
                                        .header("Random-Header", UUID.randomUUID().toString())
                                        .cookie("random-cookie", "gass")
                                .build())
                ))
                .build();
    }

    public Flux<User> getGithubUsers() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(User.class)
                .doFirst(() -> log.info("FIRST - Calling Github-api to get user data"))
                .doOnNext(user -> log.info("ONNEXT - getting user data from api: \n {}", user))
                .doOnError(err -> log.error("ERROR - Error while retrieving data from the api reasons: {}", err.getMessage()))
                .doOnComplete(() -> log.info("COMPLETE - data is retrieved from the api"))
                .doOnTerminate(() -> log.info("TERMINATE"))
                .doFinally(signalType -> log.info("FINALLY"));
    }
}
