package com.service;

import com.dao.FilmshowRepository;
import com.dao.HallRepository;
import com.dao.SeatRepository;
import com.domain.Hall;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultHallService implements HallService {

    private final HallRepository hallRepository;
    private final FilmshowRepository filmshowRepository;
    private final SeatRepository seatRepository;

    public DefaultHallService(HallRepository hallRepository, FilmshowRepository filmshowRepository,
            SeatRepository seatRepository) {
        this.hallRepository = hallRepository;
        this.filmshowRepository = filmshowRepository;
        this.seatRepository = seatRepository;
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
    public boolean checkHallInFilmshow(Hall hall) {
        return !filmshowRepository.findAllByHall(hall).isEmpty();
    }

    @Override
    public boolean checkHallInSeat(Hall hall) {
        return !seatRepository.findAllByHall(hall).isEmpty();
    }
}
