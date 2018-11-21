package com.dao;

import com.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, BigInteger> {

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

}
