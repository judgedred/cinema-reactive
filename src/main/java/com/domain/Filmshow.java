package com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Filmshow")
public class Filmshow implements Serializable {

    @Id
    @Column(name = "filmshow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmshowId;
    @Column(name = "date_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @NotNull
    private Date dateTime;
    @ManyToOne
    @JoinColumn(name = "film_id")
    @NotNull
    private Film film;
    @ManyToOne
    @JoinColumn(name = "hall_id")
    @NotNull
    private Hall hall;

    public Integer getFilmshowId() {
        return filmshowId;
    }

    public void setFilmshowId(Integer filmshowId) {
        this.filmshowId = filmshowId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm");
        return film.getFilmName() + " " + hall.getHallName() + " " + dateFormat.format(dateTime);
    }
}