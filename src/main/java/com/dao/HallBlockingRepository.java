package com.dao;

import com.domain.Hall;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface HallBlockingRepository extends MongoRepository<Hall, BigInteger> {

}