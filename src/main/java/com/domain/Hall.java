package com.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Hall")
public class Hall implements Serializable {

    @Id
    @Column(name = "hall_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hallId;
    @Column(name = "hall_number", nullable = false)
    @NotNull
    private Integer hallNumber;
    @Column(name = "hall_name", length = 45, nullable = false)
    @NotNull
    @NotEmpty
    private String hallName;

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
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