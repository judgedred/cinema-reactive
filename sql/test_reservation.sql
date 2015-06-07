Select login, price, time, film_name, hall_name, seat_number From Reservation R Inner Join Ticket T On R.ticket_id = T.ticket_id Inner Join User U On R.user_id = U.user_id
Inner Join Filmshow F On T.filmshow_id = F.filmshow_id Inner Join Hall H On F.hall_id = H.hall_id
Inner Join Seat S On T.seat_id = S.seat_id Inner Join Film Fi On F.film_id = Fi.film_id;
