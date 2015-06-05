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
	public boolean equals(Object other)
	{
		if(!super.equals(other))
		{
			return false;
		}
		if(this == other)
		{
			return true;
		}
		if(other == null)
		{
			return false;
		}
		if(this.getClass() != other.getClass())
		{
			return false;
		}
		Hall obj = (Hall)other;
		if(this.hallId == obj.hallId && this.hallNumber == obj.hallNumber && this.hallName == obj.hallName)
		{
			return true;
		}
		return false; 
	}
	
	@Override 
	public int hashCode()
	{
		return 22+42*hallId+hallNumber;
	}

	@Override
	public String toString()
	{
		return hallNumber + " " + hallName;
	}
}