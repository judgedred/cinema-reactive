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

		Reservation that = (Reservation) o;

		if(reservationId != that.reservationId)
		{
			return false;
		}
		if(!ticket.equals(that.ticket))
		{
			return false;
		}
		if(!user.equals(that.user))
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		return reservationId;
	}

	@Override
	public String toString()
	{
		return user.getLogin() + " " + ticket.getFilmshow().getFilm().getFilmName();
	}
}