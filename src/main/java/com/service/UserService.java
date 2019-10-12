package com.service;

import com.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface UserService {

    Mono<User> save(User user);

    Mono<Void> delete(User user);

    Flux<User> getUserAll();

    Mono<User> getUserById(BigInteger id);

    Mono<User> getUserByLogin(String login);

    Mono<User> getUserByEmail(String email);

    Mono<User> authenticateAdmin(User user);

    Mono<User> authenticateUser(User user);

}
