package com.empirica.tourismagency.field;

import javax.persistence.*;

@Entity
public class TourToReservationItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	@ManyToOne
	@JoinColumn(name="tour_id")
	private Tour tour;
	
	@ManyToOne
	@JoinColumn(name="reservation_item_id")
	private ReservationItem reservationItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public com.empirica.tourismagency.field.ReservationItem getReservationItem() {
		return reservationItem;
	}

	public void setReservationItem(ReservationItem reservationItem) {
		this.reservationItem = reservationItem;
	}
}
