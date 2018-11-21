package com.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Document
public class Filmshow implements Serializable {

    @Id
    private BigInteger filmshowId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;
    @NotNull
    private Film film;
    @NotNull
    private Hall hall;

    public Filmshow() {
    }

    public Filmshow(LocalDateTime dateTime, Film film, Hall hall) {
        this.dateTime = dateTime;
        this.film = film;
        this.hall = hall;
    }

    public BigInteger getFilmshowId() {
        return filmshowId;
    }

    public void setFilmshowId(BigInteger filmshowId) {
        this.filmshowId = filmshowId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Filmshow filmshow = (Filmshow) o;

        if (!Objects.equals(filmshowId, filmshow.filmshowId)) {
            return false;
        }
        if (!dateTime.equals(filmshow.dateTime)) {
            return false;
        }
        if (!film.equals(filmshow.film)) {
            return false;
        }
        if (!hall.equals(filmshow.hall)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return filmshowId != null ? filmshowId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return film.getFilmName() + " "
                + hall.getHallName() + " "
                + dateTime.format(DateTimeFormatter.ofPattern("MM.dd HH:mm"));
    }
}