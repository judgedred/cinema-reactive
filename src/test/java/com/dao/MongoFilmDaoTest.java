package com.dao;

import com.domain.Film;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MongoFilmDaoTest {

    @Autowired
    private FilmRepository filmRepository;

    @Test
    public void createTest() {
        String filmNameExpected = "testFilmName";
        String descriptionExpected = "testFilmDescription";
        Film film = createFilm(null, filmNameExpected, descriptionExpected);
        assertNotNull(film);
        assertThat(film.getFilmName(), is(filmNameExpected));
        assertThat(film.getDescription(), is(descriptionExpected));
        filmRepository.delete(film);
    }

    @Test
    public void getByIdTest() throws DaoException {
        Film film = createTestFilm();
        Optional<Film> filmTest = filmRepository.findById(film.getFilmId());
        assertTrue(filmTest.isPresent());
        filmRepository.delete(film);
    }

    @Test
    public void updateTest() {
        Film film = createTestFilm();
        String filmNameExpected = "updatedFilmName";
        String descriptionExpected = "updatedFilmDescription";
        film.setFilmName(filmNameExpected);
        film.setDescription(descriptionExpected);
        filmRepository.save(film);
        Optional<Film> filmOptional = filmRepository.findById(film.getFilmId());
        assertTrue(filmOptional.isPresent());
        Film filmUpdated = filmOptional.get();
        assertThat(filmUpdated.getFilmName(), is(filmNameExpected));
        assertThat(filmUpdated.getDescription(), is(descriptionExpected));
        filmRepository.delete(film);
    }

    @Test
    public void deleteTest() {
        Film film = createTestFilm();
        filmRepository.delete(film);
        assertFalse(filmRepository.findById(film.getFilmId()).isPresent());
    }

    @Test
    public void getAllFilmsTest() throws DaoException {
        Film film = createTestFilm();
        List<Film> films = filmRepository.findAll();
        assertNotNull(films);
        assertTrue(films.size() > 0);
        filmRepository.delete(film);
    }

    private Film createFilm(BigInteger filmId, String filmName, String description) {
        Film film = new Film();
        film.setFilmId(filmId);
        film.setFilmName(filmName);
        film.setDescription(description);
        return filmRepository.save(film);
    }

    private Film createTestFilm() {
        return createFilm(BigInteger.valueOf(555), "testFilm", "testFilm");
    }
}
