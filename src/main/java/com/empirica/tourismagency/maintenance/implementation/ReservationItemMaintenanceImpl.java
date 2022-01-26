package com.empirica.tourismagency.maintenance.implementation;

import java.math.BigDecimal;
import java.util.List;


import com.empirica.tourismagency.field.*;
import com.empirica.tourismagency.repository.TourToReservationItemRepository;
import com.empirica.tourismagency.repository.ReservationItemRepository;
import com.empirica.tourismagency.maintenance.ReservationItemMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservationItemMaintenanceImpl implements ReservationItemMaintenance {

	@Autowired
	private ReservationItemRepository reservationItemRepository;

	@Autowired
	private TourToReservationItemRepository tourToReservationItemRepository;

	public List<ReservationItem> findByReservation(Reservation reservation) {
		return reservationItemRepository.findByReservation(reservation);
	}

	public ReservationItem updateReservationItem(ReservationItem reservationItem) {
		BigDecimal bigDecimal = new BigDecimal(reservationItem.getTour().getPrice()).multiply(new BigDecimal(reservationItem.getQty()));

		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		reservationItem.setSubtotal(bigDecimal);

		reservationItemRepository.save(reservationItem);

		return reservationItem;
	}

	public ReservationItem addTourToReservationItem(Tour tour, User user, int qty) {
		List<ReservationItem> reservationItemList = findByReservation(user.getReservation());

		for(ReservationItem reservationItem : reservationItemList) {
			if(tour.getId() == reservationItem.getTour().getId()) {
				reservationItem.setQty(reservationItem.getQty()+qty);
				reservationItem.setSubtotal(new BigDecimal(tour.getPrice()).multiply(new BigDecimal(qty)));
				reservationItemRepository.save(reservationItem);
				return reservationItem;
			}
		}

		ReservationItem reservationItem = new ReservationItem();
		reservationItem.setReservation(user.getReservation());
		reservationItem.setTour(tour);

		reservationItem.setQty(qty);
		reservationItem.setSubtotal(new BigDecimal(tour.getPrice()).multiply(new BigDecimal(qty)));
		reservationItem = reservationItemRepository.save(reservationItem);

		TourToReservationItem tourToReservationItem = new TourToReservationItem();
		tourToReservationItem.setTour(tour);
		tourToReservationItem.setReservationItem(reservationItem);
		tourToReservationItemRepository.save(tourToReservationItem);

		return reservationItem;
	}

	public ReservationItem findById(Long id) {
		return reservationItemRepository.findById(id)
				.orElse(null);
	}

	public void removeReservationItem(ReservationItem reservationItem) {
		tourToReservationItemRepository.deleteByReservationItem(reservationItem);
		reservationItemRepository.delete(reservationItem);
	}

	public ReservationItem save(ReservationItem reservationItem){
		return  reservationItemRepository.save(reservationItem);
	}

	public 	List<ReservationItem> findByOrder(Order order){

		return reservationItemRepository.findByOrder(order);
	}



}
