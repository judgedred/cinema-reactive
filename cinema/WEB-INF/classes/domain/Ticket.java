package domain;


public class Ticket
{
	private int ticketId;
	private float price;
	private Filmshow filmshow;
	private Seat seat;

	public int getTicketId()
	{
		return ticketId;
	}

	public void setTicketId(int ticketId)
	{
		this.ticketId = ticketId;
	}

	public float getPrice()
	{
		return price;
	}

	public void setPrice(float price)
	{
		this.price = price;
	}

	public Filmshow getFilmshow()
	{
		return filmshow;
	}

	public void setFilmshow(Filmshow filmshow)
	{
		this.filmshow = filmshow;
	}

	public Seat getSeat()
	{
		return seat;
	}
	
	public void setSeat(Seat seat)
	{
		this.seat = seat;
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
		Ticket obj = (Ticket)other;
		if(this.ticketId == obj.ticketId && this.price == obj.price && this.filmshow.equals(obj.filmshow) && this.seat.equals(obj.seat))
		{
			return true;
		}
		return false; 
	}
	
	@Override 
	public int hashCode()
	{
		return 25+45*ticketId;
	}

	@Override
	public String toString()
	{
		return filmshow.getFilm().getFilmName() + " " + filmshow.getDateTime() + " " + seat.getSeatNumber() + " " + seat.getRowNumber() + " " + price;
	}
}