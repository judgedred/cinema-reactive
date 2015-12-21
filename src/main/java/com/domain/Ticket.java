package com.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@Table(name = "Ticket")
public class Ticket implements Serializable
{
    @Id
	@GeneratedValue
    @Column(name = "ticket_id")
	private Integer ticketId;
    @Column(name = "price", nullable = false)
    @NotNull
	private Float price;
    @ManyToOne
    @JoinColumn(name = "filmshow_id")
    @NotNull
	private Filmshow filmshow;
    @OneToOne
    @JoinColumn(name = "seat_id")
    @NotNull
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

	public void setPrice(Float price)
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
        return ticketId != null ? ticketId.hashCode() : 0;
    }

    @Override
	public String toString()
	{
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm");
        return filmshow.getFilm().getFilmName() + " " + dateFormat.format(filmshow.getDateTime()) + " " + seat.getSeatNumber() + " " + "место" + " " + seat.getRowNumber() + " " + "ряд" + " " + String.format("%d",price.intValue());
	}
}