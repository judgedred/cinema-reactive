package com.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Document
public class Film implements Serializable {

    @Id
    private BigInteger filmId;
    @NotNull
    @NotEmpty
    private String filmName;
    @NotNull
    @NotEmpty
    private String description;

    public Film() {
    }

    public Film(String filmName, String description) {
        this.filmName = filmName;
        this.description = description;
    }

    public BigInteger getFilmId() {
        return filmId;
    }

    public void setFilmId(BigInteger filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Film film = (Film) o;

        if (!Objects.equals(filmId, film.filmId)) {
            return false;
        }
        if (!description.equals(film.description)) {
            return false;
        }
        if (!filmName.equals(film.filmName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return filmId != null ? filmId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return filmName + " " + description;
    }
}


