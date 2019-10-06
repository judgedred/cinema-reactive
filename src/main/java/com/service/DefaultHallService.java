package com.service;

import com.dao.HallRepository;
import com.domain.Hall;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public class DefaultHallService implements HallService {

    private final HallRepository hallRepository;

    public DefaultHallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Mono<Hall> save(Hall hall) {
        return hallRepository.save(hall);
    }

    @Override
    public Mono<Void> delete(Hall hall) {
        return hallRepository.delete(hall);
    }

    @Override
    public Flux<Hall> getHallAll() {
        return hallRepository.findAll();
    }

    @Override
    public Mono<Hall> getHallById(BigInteger id) {
        return hallRepository.findById(id);
    }

    @Override
    public Mono<Hall> getHallByNumber(Integer hallNumber) {
        return hallRepository.findByHallNumber(hallNumber);
    }

}
