package domain;


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
		Seat obj = (Seat)other;
		if(this.seatId == obj.seatId && this.seatNumber == obj.seatNumber && this.rowNumber == obj.rowNumber && this.hall.equals(obj.hall))
		{
			return true;
		}
		return false; 
	}
	
	@Override 
	public int hashCode()
	{
		return 24+44*seatId+seatNumber;
	}

	@Override
	public String toString()
	{
		return seatNumber + " " + rowNumber + " " + hall.getHallName();
	}
}