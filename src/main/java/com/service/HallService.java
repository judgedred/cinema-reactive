package com.service;

import com.domain.Hall;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface HallService {

    Mono<Hall> save(Hall hall);

    Mono<Void> delete(Hall hall);

    Flux<Hall> getHallAll();

    Mono<Hall> getHallById(BigInteger id);

    Mono<Hall> getHallByNumber(Integer hallNumber);

}
