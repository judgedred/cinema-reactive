package com.domain;


public class Hall
{
	private int hallId;
	private int hallNumber;
	private String hallName;

	public int getHallId()
	{
		return hallId;
	}

	public void setHallId(int hallId)
	{
		this.hallId = hallId;
	}

	public int getHallNumber()
	{
		return hallNumber;
	}

	public void setHallNumber(int hallNumber)
	{
		this.hallNumber = hallNumber;
	}

	public String getHallName()
	{	
		return hallName;
	}

	public void setHallName(String hallName)
	{
		this.hallName = hallName;
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

		Hall hall = (Hall) o;

		if(hallId != hall.hallId)
		{
			return false;
		}
		if(hallNumber != hall.hallNumber)
		{
			return false;
		}
		if(!hallName.equals(hall.hallName))
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = hallId;
		result = 31 * result + hallNumber;
		return result;
	}

	@Override
	public String toString()
	{
		return hallNumber + " " + hallName;
	}
}