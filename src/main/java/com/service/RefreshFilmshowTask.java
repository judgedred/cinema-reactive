package com.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RefreshFilmshowTask {

    private final FilmshowService filmshowService;

    public RefreshFilmshowTask(FilmshowService filmshowService) {
        this.filmshowService = filmshowService;
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void invoke() {
        filmshowService.refreshFilmshowToday().block();
    }
}
