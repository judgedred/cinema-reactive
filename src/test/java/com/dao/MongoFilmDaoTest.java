package com.dao;

import com.CinemaTestConfiguration;
import com.domain.Film;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CinemaTestConfiguration.class)
public class MongoFilmDaoTest {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TestDataRepository testDataRepository;

    @Test
    public void createTest() {
        String filmNameExpected = "testFilmName";
        String descriptionExpected = "testFilmDescription";
        Film film = testDataRepository.createFilm(null, filmNameExpected, descriptionExpected);
        assertNotNull(film);
        assertThat(film.getFilmName(), is(filmNameExpected));
        assertThat(film.getDescription(), is(descriptionExpected));
        filmRepository.delete(film).block();
    }

    @Test
    public void getByIdTest() {
        Film film = testDataRepository.createTestFilm();
        Optional<Film> filmTest = filmRepository.findById(film.getFilmId()).blockOptional();
        assertTrue(filmTest.isPresent());
        filmRepository.delete(film).block();
    }

    @Test
    public void updateTest() {
        Film film = testDataRepository.createTestFilm();
        String filmNameExpected = "updatedFilmName";
        String descriptionExpected = "updatedFilmDescription";
        film.setFilmName(filmNameExpected);
        film.setDescription(descriptionExpected);
        filmRepository.save(film).block();
        Optional<Film> filmOptional = filmRepository.findById(film.getFilmId()).blockOptional();
        assertTrue(filmOptional.isPresent());
        Film filmUpdated = filmOptional.get();
        assertThat(filmUpdated.getFilmName(), is(filmNameExpected));
        assertThat(filmUpdated.getDescription(), is(descriptionExpected));
        filmRepository.delete(film).block();
    }

    @Test
    public void deleteTest() {
        Film film = testDataRepository.createTestFilm();
        filmRepository.delete(film).block();
        assertFalse(filmRepository.findById(film.getFilmId()).blockOptional().isPresent());
    }

    @Test
    public void getAllFilmsTest() {
        Film film = testDataRepository.createTestFilm();
        List<Film> films = filmRepository.findAll().collectList().block();
        assertNotNull(films);
        assertTrue(films.size() > 0);
        filmRepository.delete(film).block();
    }

}
