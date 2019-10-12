package com.dao;

import com.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface UserBlockingRepository extends MongoRepository<User, BigInteger> {

}
