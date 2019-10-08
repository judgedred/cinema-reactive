package com.dao;

import com.domain.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface TicketBlockingRepository extends MongoRepository<Ticket, BigInteger> {

}
