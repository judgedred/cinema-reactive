package com.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Hall")
public class Hall
{
    @Id
    @Column(name = "hall_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer hallId;
    @Column(name = "hall_number")
	private Integer hallNumber;
    @Column(name = "hall_name", length = 45)
	private String hallName;

	public Integer getHallId()
	{
		return hallId;
	}

	public void setHallId(Integer hallId)
	{
		this.hallId = hallId;
	}

	public Integer getHallNumber()
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

		if(!Objects.equals(hallId, hall.hallId))
		{
			return false;
		}
		if(!Objects.equals(hallNumber, hall.hallNumber))
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