package com.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Ticket")
public class Ticket
{
    @Id
    @Column(name = "ticket_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ticketId;
    @Column(name = "price")
	private Float price;
    @ManyToOne
    @JoinColumn(name = "filmshow_id")
	private Filmshow filmshow;
    @OneToOne
    @JoinColumn(name = "seat_id")
	private Seat seat;

	public Integer getTicketId()
	{
		return ticketId;
	}
	public void setTicketId(Integer ticketId)
	{
		this.ticketId = ticketId;
	}

	public Float getPrice()
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

		Ticket ticket = (Ticket) o;

		if(Float.compare(ticket.price, price) != 0)
		{
			return false;
		}
		if(!Objects.equals(ticketId, ticket.ticketId))
		{
			return false;
		}
		if(!filmshow.equals(ticket.filmshow))
		{
			return false;
		}
		if(!seat.equals(ticket.seat))
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		return ticketId;
	}

	@Override
	public String toString()
	{
		return filmshow.getFilm().getFilmName() + " " + filmshow.getDateTime() + " " + seat.getSeatNumber() + " " + seat.getRowNumber() + " " + price;
	}
}