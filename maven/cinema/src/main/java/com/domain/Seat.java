package com.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Seat")
public class Seat implements Serializable
{
    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer seatId;
    @Column(name = "seat_number", nullable = false)
	private Integer seatNumber;
    @Column(name = "row_number", nullable = false)
	private Integer rowNumber;
    @ManyToOne
    @JoinColumn(name = "hall_id")
	private Hall hall;

	public Integer getSeatId()
	{
		return seatId;
	}

	public void setSeatId(Integer seatId)
	{
		this.seatId = seatId;
	}

	public Integer getSeatNumber()
	{
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber)
	{
		this.seatNumber = seatNumber;
	}

	public Integer getRowNumber()
	{
		return rowNumber;
	}

	public void setRowNumber(int rowNumber)
	{
		this.rowNumber = rowNumber;
	}

	public Hall getHall()
	{
		return hall;
	}

	public void setHall(Hall hall)
	{
		this.hall = hall;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(o == null || getClass() != o.getClass())
		{
			return false;
		}

		Seat seat = (Seat) o;

		if(!Objects.equals(rowNumber, seat.rowNumber))
		{
			return false;
		}
		if(!Objects.equals(seatId, seat.seatId))
		{
			return false;
		}
		if(!Objects.equals(seatNumber, seat.seatNumber))
		{
			return false;
		}
		if(!hall.equals(seat.hall))
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = seatId;
		result = 31 * result + seatNumber;
		result = 31 * result + rowNumber;
		return result;
	}

	@Override
	public String toString()
	{
		return seatNumber + " " + "место" + " " + rowNumber + " " + "ряд" + " " + hall.getHallName();
	}
}