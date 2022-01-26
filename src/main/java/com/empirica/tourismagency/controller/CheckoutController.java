package com.empirica.tourismagency.controller;


import com.empirica.tourismagency.field.*;
import com.empirica.tourismagency.maintenance.*;
import com.empirica.tourismagency.applicability.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
public class CheckoutController {

	private Payment payment = new Payment();

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserMaintenance userMaintenance;

	@Autowired
	private ReservationItemMaintenance reservationItemMaintenance;
	@Autowired
	private ReservationMaintenance reservationMaintenance;

	@Autowired
	private PaymentMaintenance paymentMaintenance;
	@Autowired
	private UserPaymentMaintenance userPaymentMaintenance;


	@Autowired
	private OrderMaintenance orderMaintenance;


	@RequestMapping("/checkout")
	public String checkout(
			@RequestParam(value="id") Long reservationId,
			@RequestParam(value="missingRequiredField", required=false) boolean missingRequiredField, Model model, Principal principal){
		User user = userMaintenance.findByUsername(principal.getName());

		if(reservationId != user.getReservation().getId()) {
			return "badRequestPage";
		}

		List<ReservationItem> reservationItemList = reservationItemMaintenance.findByReservation(user.getReservation());

		if(reservationItemList.size() == 0) {
			model.addAttribute("emptyReservation", true);
			return "forward:/reservation/reservations";
		}

		for (ReservationItem reservationItem : reservationItemList) {
			if(reservationItem.getTour().getQuantity() < reservationItem.getQty()) {
				model.addAttribute("notEnoughStock", true);
				return "forward:/reservation/reservations";
			}
		}


		List<UserPayment> userPaymentList = user.getUserPaymentList();
		model.addAttribute("userPaymentList", userPaymentList);

		if (userPaymentList.size() == 0) {
			model.addAttribute("emptyPaymentList", true);
		} else {
			model.addAttribute("emptyPaymentList", false);
		}


		for (UserPayment userPayment : userPaymentList) {
			if(userPayment.isDefaultPayment()) {
				paymentMaintenance.setByUserPayment(userPayment, payment);
			}
		}
		model.addAttribute("payment", payment);
		model.addAttribute("reservationItemList", reservationItemList);
		model.addAttribute("reservation", user.getReservation());

		if(missingRequiredField) {
			model.addAttribute("missingRequiredField", true);
		}

		return "checkout";

	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String checkoutPost(
			@ModelAttribute("payment") Payment payment, Principal principal,
			Model model) {
		Reservation reservation = userMaintenance.findByUsername(principal.getName()).getReservation();

		List<ReservationItem> reservationItemList = reservationItemMaintenance.findByReservation(reservation);
		model.addAttribute("reservationItemList", reservationItemList);


		User user = userMaintenance.findByUsername(principal.getName());

		Order order = orderMaintenance.createOrder(reservation, payment, user);

		mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));

		reservationMaintenance.clearReservation(reservation);


		return "orderSubmittedPage";
	}



	@RequestMapping("/setPaymentMethod")
	public String setPaymentMethod(
			@RequestParam("userPaymentId") Long userPaymentId,
			Principal principal, Model model
			) {
		User user = userMaintenance.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentMaintenance.findById(userPaymentId);
		UserBilling userBilling = userPayment.getUserBilling();

		if(userPayment.getUser().getId() != user.getId()){
			return "badRequestPage";
		} else {
			paymentMaintenance.setByUserPayment(userPayment, payment);

			List<ReservationItem> reservationItemList = reservationItemMaintenance.findByReservation(user.getReservation());

			model.addAttribute("payment", payment);
			model.addAttribute("reservationItemList", reservationItemList);
			model.addAttribute("reservation", user.getReservation());

			List<UserPayment> userPaymentList = user.getUserPaymentList();

			model.addAttribute("userPaymentList", userPaymentList);
			model.addAttribute("classActivePayment", true);
			model.addAttribute("emptyPaymentList", false);

			return "checkout";
		}
	}

}
