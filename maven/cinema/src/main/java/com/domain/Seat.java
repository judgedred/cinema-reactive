package com.domain;


public class Seat
{
	private int seatId;
	private int seatNumber;
	private int rowNumber;
	private Hall hall;

	public int getSeatId()
	{
		return seatId;
	}

	public void setSeatId(int seatId)
	{
		this.seatId = seatId;
	}

	public int getSeatNumber()
	{
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber)
	{
		this.seatNumber = seatNumber;
	}

	public int getRowNumber()
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

		if(rowNumber != seat.rowNumber)
		{
			return false;
		}
		if(seatId != seat.seatId)
		{
			return false;
		}
		if(seatNumber != seat.seatNumber)
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
		return seatNumber + " " + rowNumber + " " + hall.getHallName();
	}
}