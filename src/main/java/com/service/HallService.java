package com.service;

import com.domain.Hall;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface HallService {

    Hall save(Hall hall);

    void delete(Hall hall);

    List<Hall> getHallAll();

    Optional<Hall> getHallById(BigInteger id);

    Optional<Hall> getHallByNumber(Integer hallNumber);

    boolean checkHallInSeat(Hall hall);
}
