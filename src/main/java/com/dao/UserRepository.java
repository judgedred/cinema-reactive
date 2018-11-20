package com.dao;

import com.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

public interface UserRepository extends MongoRepository<User, BigInteger> {

    List<User> findUserByLogin(String login);

    List<User> findUserByEmail(String email);

}
