package com.dao;

import com.domain.Film;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        Film filmTest = filmRepository.getFilmByfilmId(film.getFilmId());
        assertNotNull(filmTest);
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
        Film filmUpdated = filmRepository.getFilmByfilmId(film.getFilmId());
        assertNotNull(filmUpdated);
        assertThat(filmUpdated.getFilmName(), is(filmNameExpected));
        assertThat(filmUpdated.getDescription(), is(descriptionExpected));
        filmRepository.delete(film);
    }

    @Test
    public void deleteTest() {
        Film film = createTestFilm();
        filmRepository.delete(film);
        assertNull(filmRepository.getFilmByfilmId(film.getFilmId()));
    }

    @Test
    public void testGetFilmAll() throws DaoException {
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
