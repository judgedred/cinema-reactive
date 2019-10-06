package com.service;

import com.dao.UserRepository;
import com.domain.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User authenticateAdmin(User user) {
        return Optional.of(user.getLogin())
                .filter(login -> login.equals("admin"))
                .flatMap(userRepository::findByLogin)
                .filter(adminUser -> adminUser.getPassword().equals(HashGenerator.invoke(user.getPassword())))
                .orElse(null);
    }

    @Override
    public User authenticateUser(User user) {
        return userRepository.findByLogin(user.getLogin())
                .filter(foundUser -> foundUser.getPassword().equals(HashGenerator.invoke(user.getPassword())))
                .orElse(null);
    }

    @Override
    public boolean checkLogin(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
