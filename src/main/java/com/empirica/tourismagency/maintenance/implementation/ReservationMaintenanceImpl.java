package com.empirica.tourismagency.maintenance.implementation;


import java.math.BigDecimal;
import java.util.List;

import com.empirica.tourismagency.field.ReservationItem;
import com.empirica.tourismagency.field.Reservation;
import com.empirica.tourismagency.repository.ReservationRepository;
import com.empirica.tourismagency.maintenance.ReservationItemMaintenance;
import com.empirica.tourismagency.maintenance.ReservationMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservationMaintenanceImpl implements ReservationMaintenance {
	
	@Autowired
	private ReservationItemMaintenance reservationItemMaintenance;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	public Reservation updateReservation(Reservation reservation) {
		BigDecimal reservationTotal = new BigDecimal(0);
		
		List<ReservationItem> reservationItemList = reservationItemMaintenance.findByReservation(reservation);
		
		for (ReservationItem reservationItem : reservationItemList) {
			if(reservationItem.getTour().getQuantity() > 0) {
				reservationItemMaintenance.updateReservationItem(reservationItem);
				reservationTotal = reservationTotal.add(reservationItem.getSubtotal());
			}
		}
		
		reservation.setGrandTotal(reservationTotal);
		
		reservationRepository.save(reservation);
		
		return reservation;
	}
    public 	void clearReservation(Reservation reservation){
		List<ReservationItem> reservationItemList = reservationItemMaintenance.findByReservation(reservation);

		for(ReservationItem reservationItem : reservationItemList) {
			reservationItem.setReservation(null);
			reservationItemMaintenance.save(reservationItem);
		}

		reservation.setGrandTotal(new BigDecimal(0));

		reservationRepository.save(reservation);
	}


}
