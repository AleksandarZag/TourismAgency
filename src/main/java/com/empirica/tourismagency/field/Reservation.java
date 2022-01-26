package com.empirica.tourismagency.field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	private BigDecimal GrandTotal;
	
	@OneToMany(mappedBy="reservation", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<ReservationItem> reservationItemList;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getGrandTotal() {
		return GrandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		GrandTotal = grandTotal;
	}

	public List<ReservationItem> getReservationItemList() {
		return reservationItemList;
	}

	public void setReservationItemList(List<ReservationItem> reservationItemList) {
		this.reservationItemList = reservationItemList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
