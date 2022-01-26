package com.empirica.tourismagency.controller;

import com.empirica.tourismagency.field.Tour;
import com.empirica.tourismagency.field.ReservationItem;
import com.empirica.tourismagency.field.Reservation;
import com.empirica.tourismagency.field.User;
import com.empirica.tourismagency.maintenance.TourMaintenance;
import com.empirica.tourismagency.maintenance.ReservationItemMaintenance;
import com.empirica.tourismagency.maintenance.ReservationMaintenance;
import com.empirica.tourismagency.maintenance.UserMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private UserMaintenance userMaintenance;

	@Autowired
	private ReservationItemMaintenance reservationItemMaintenance;


	@Autowired
	private TourMaintenance tourMaintenance;

	@Autowired
	private ReservationMaintenance reservationMaintenance;

	@RequestMapping("/reservations")
	public String reservation(Model model, Principal principal) {
		User user = userMaintenance.findByUsername(principal.getName());
		Reservation reservation = user.getReservation();

		List<ReservationItem> reservationItemList = reservationItemMaintenance.findByReservation(reservation);

		reservationMaintenance.updateReservation(reservation);

		model.addAttribute("reservationItemList", reservationItemList);
		model.addAttribute("reservation", reservation);

		return "reservation";
	}

	@RequestMapping("/addItem")
	public String addItem(
			@ModelAttribute("tour") Tour tour,
			@ModelAttribute("qty") String qty,
			Model model, Principal principal
	) {
		User user = userMaintenance.findByUsername(principal.getName());
		tour = tourMaintenance.findOne(tour.getId());

		if(Integer.parseInt(qty) > tour.getQuantity()) {
			model.addAttribute("notEnoughStock", true);
			return "forward:/tourDetail?id="+tour.getId();
		}

		ReservationItem reservationItem = reservationItemMaintenance.addTourToReservationItem(tour, user, Integer.parseInt(qty));
		model.addAttribute("addTourSuccess", true);

		return "forward:/tourDetail?id="+tour.getId();
	}
	@RequestMapping("/updateReservationItem")
	public String updateReservation(
		@ModelAttribute("id") Long reservationItemId,
				@ModelAttribute("qty") int qty
	){
		ReservationItem reservationItem = reservationItemMaintenance.findById(reservationItemId);
		reservationItem.setQty(qty);
		reservationItemMaintenance.updateReservationItem(reservationItem);
		return "forward:/reservation/reservations";
	}
	@RequestMapping("/removeItem")
	public String removeItem(@RequestParam("id") Long id) {
		reservationItemMaintenance.removeReservationItem(reservationItemMaintenance.findById(id));

		return "forward:/reservation/reservations";
	}

}
