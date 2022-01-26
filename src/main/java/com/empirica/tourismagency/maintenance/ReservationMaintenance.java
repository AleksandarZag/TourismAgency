package com.empirica.tourismagency.maintenance;


import com.empirica.tourismagency.field.Reservation;
import com.empirica.tourismagency.field.ReservationItem;



public interface ReservationMaintenance {
	Reservation updateReservation(Reservation reservation);

	void clearReservation(Reservation reservation);
}
