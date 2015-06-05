package com.domain;


public class Reservation
{
	private int reservationId;
	private User user;
	private Ticket ticket;

	public int getReservationId()
	{
		return reservationId;
	}
	
	public void setReservationId(int reservationId)
	{
		this.reservationId = reservationId;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Ticket getTicket()
	{	
		return ticket;
	}

	public void setTicket(Ticket ticket)
	{
		this.ticket = ticket;
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
		Reservation obj = (Reservation)other;
		if(this.reservationId == obj.reservationId && this.user.equals(obj.user) && this.ticket.equals(obj.ticket))
		{
			return true;
		}
		return false; 
	}
	
	@Override 
	public int hashCode()
	{
		return 23+43*reservationId;
	}

	@Override
	public String toString()
	{
		return user.getLogin() + " " + ticket.getFilmshow().getFilm().getFilmName();
	}
}