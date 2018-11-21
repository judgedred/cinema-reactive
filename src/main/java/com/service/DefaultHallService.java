package com.service;

import com.dao.HallRepository;
import com.domain.Hall;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultHallService implements HallService {

    private final HallRepository hallRepository;
    private final FilmshowService filmshowService;
    private final SeatService seatService;

    public DefaultHallService(HallRepository hallRepository, FilmshowService filmshowService, SeatService seatService) {
        this.hallRepository = hallRepository;
        this.filmshowService = filmshowService;
        this.seatService = seatService;
    }

    @Override
    public Hall save(Hall hall) {
        return hallRepository.save(hall);
    }

    @Override
    public void delete(Hall hall) {
        hallRepository.delete(hall);
    }

    @Override
    public List<Hall> getHallAll() {
        return hallRepository.findAll();
    }

    @Override
    public Optional<Hall> getHallById(BigInteger id) {
        return hallRepository.findById(id);
    }

    @Override
    public Optional<Hall> getHallByNumber(Integer hallNumber) {
        return hallRepository.findByHallNumber(hallNumber);
    }

    @Override
    public boolean checkHallInFilmshow(Hall hall) {
        return !filmshowService.getFilmshowByHall(hall).isEmpty();
    }

    @Override
    public boolean checkHallInSeat(Hall hall) {
        return !seatService.getSeatAllByHall(hall).isEmpty();
    }
}
