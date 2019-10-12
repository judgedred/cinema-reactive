package com.dao;

import com.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface UserRepository extends ReactiveMongoRepository<User, BigInteger> {

    Mono<User> findByLogin(String login);

    Mono<User> findByEmail(String email);

}
