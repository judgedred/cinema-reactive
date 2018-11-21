package com.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Document
public class Hall implements Serializable {

    @Id
    private BigInteger hallId;
    @NotNull
    private Integer hallNumber;
    @NotNull
    @NotEmpty
    private String hallName;

    public Hall() {
    }

    public Hall(Integer hallNumber, String hallName) {
        this.hallNumber = hallNumber;
        this.hallName = hallName;
    }

    public BigInteger getHallId() {
        return hallId;
    }

    public void setHallId(BigInteger hallId) {
        this.hallId = hallId;
    }

    public Integer getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(Integer hallNumber) {
        this.hallNumber = hallNumber;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Hall hall = (Hall) o;

        if (!Objects.equals(hallId, hall.hallId)) {
            return false;
        }
        if (!Objects.equals(hallNumber, hall.hallNumber)) {
            return false;
        }
        if (!hallName.equals(hall.hallName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return hallId != null ? hallId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return hallNumber + " " + hallName;
    }

}