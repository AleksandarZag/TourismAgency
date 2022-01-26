package com.empirica.tourismagency.field;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ReservationItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int qty;
	private BigDecimal subtotal;
	
	@OneToOne
	private Tour tour;
	
	@OneToMany(mappedBy = "reservationItem")
	@JsonIgnore
	private List<TourToReservationItem> tourToReservationItemList;

	@ManyToOne
	@JoinColumn(name="reservation_id")
	private Reservation reservation;

	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public List<TourToReservationItem> getTourToReservationItemList() {
		return tourToReservationItemList;
	}

	public void setTourToReservationItemList(List<TourToReservationItem> tourToReservationItemList) {
		this.tourToReservationItemList = tourToReservationItemList;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
