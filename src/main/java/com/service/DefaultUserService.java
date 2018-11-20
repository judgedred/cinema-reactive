package com.service;

import com.dao.ReservationRepository;
import com.dao.UserRepository;
import com.domain.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public DefaultUserService(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public User save(User user) {
        user.setPassword(HashGenerator.invoke(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> getUserAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(BigInteger id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean checkUserInReservation(User user) {
        return !reservationRepository.findAllByUser(user).isEmpty();
    }

    @Override
    public User authenticateAdmin(User user) {
        return Optional.of(user.getLogin())
                .filter(login -> login.equals("admin"))
                .map(userRepository::findAllByLogin)
                .orElse(Collections.emptyList())
                .stream()
                .findFirst()
                .filter(adminUser -> adminUser.getPassword().equals(HashGenerator.invoke(user.getPassword())))
                .orElse(null);
    }

    @Override
    public User authenticateUser(User user) {
        return userRepository.findAllByLogin(user.getLogin())
                .stream()
                .findFirst()
                .filter(foundUser -> foundUser.getPassword().equals(HashGenerator.invoke(user.getPassword())))
                .orElse(null);
    }

    @Override
    public boolean checkLogin(String login) {
        return !userRepository.findAllByLogin(login).isEmpty();
    }

    @Override
    public boolean checkEmail(String email) {
        return !userRepository.findAllByEmail(email).isEmpty();
    }
}
