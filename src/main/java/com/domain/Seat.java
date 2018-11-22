package com.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Document
public class Seat implements Serializable {

    @Id
    private BigInteger seatId;
    @NotNull
    private Integer seatNumber;
    @NotNull
    private Integer rowNumber;
    @Valid
    private Hall hall;

    public Seat() {
    }

    public Seat(Integer seatNumber, Integer rowNumber, Hall hall) {
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.hall = hall;
    }

    public BigInteger getSeatId() {
        return seatId;
    }

    public Seat setSeatId(BigInteger seatId) {
        this.seatId = seatId;
        return this;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public Seat setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public Seat setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
        return this;
    }

    public Hall getHall() {
        return hall;
    }

    public Seat setHall(Hall hall) {
        this.hall = hall;
        return this;
    }

    public String getDescription() {
        return this.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Seat seat = (Seat) o;

        if (!Objects.equals(rowNumber, seat.rowNumber)) {
            return false;
        }
        if (!Objects.equals(seatId, seat.seatId)) {
            return false;
        }
        if (!Objects.equals(seatNumber, seat.seatNumber)) {
            return false;
        }
        if (!hall.equals(seat.hall)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return seatId != null ? seatId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return seatNumber + " " + "место" + " " + rowNumber + " " + "ряд" + " " + hall.getHallName();
    }
}