package com.service;

import com.dao.UserRepository;
import com.domain.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> save(User user) {
        return Mono.just(user)
                .map(u -> u.setPassword(HashGenerator.invoke(u.getPassword())))
                .flatMap(userRepository::save);
    }

    @Override
    public Mono<Void> delete(User user) {
        return userRepository.delete(user);
    }

    @Override
    public Flux<User> getUserAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> getUserById(BigInteger id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Mono<User> authenticateAdmin(User user) {
        return Mono.just(user.getLogin())
                .filter(login -> login.equals("admin"))
                .flatMap(userRepository::findByLogin)
                .filter(adminUser -> adminUser.getPassword().equals(HashGenerator.invoke(user.getPassword())));
    }

    @Override
    public Mono<User> authenticateUser(User user) {
        return userRepository.findByLogin(user.getLogin())
                .filter(foundUser -> foundUser.getPassword().equals(HashGenerator.invoke(user.getPassword())));
    }

}
