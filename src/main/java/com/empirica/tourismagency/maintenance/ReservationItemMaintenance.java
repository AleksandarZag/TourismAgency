package com.empirica.tourismagency.maintenance;


import com.empirica.tourismagency.field.*;

import java.util.List;

public interface ReservationItemMaintenance {
	List<ReservationItem> findByReservation(Reservation reservation);
	
	ReservationItem updateReservationItem(ReservationItem reservationItem);

	ReservationItem addTourToReservationItem(Tour tour, User user, int qty);

	ReservationItem findById(Long id);

	void removeReservationItem(ReservationItem reservationItem);

	ReservationItem save(ReservationItem reservationItem);

	List<ReservationItem> findByOrder(Order order);
}
