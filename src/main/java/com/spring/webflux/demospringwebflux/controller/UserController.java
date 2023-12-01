package com.spring.webflux.demospringwebflux.controller;

import com.spring.webflux.demospringwebflux.model.User;
import com.spring.webflux.demospringwebflux.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@RequestMapping("/github/users")
public class UserController {

    private UserService userService;

    @GetMapping
    public Flux<User> getAllUsersFromGithub() {
        return userService.getGithubUsers();
    }
}
