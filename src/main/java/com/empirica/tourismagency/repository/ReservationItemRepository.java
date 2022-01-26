package com.empirica.tourismagency.repository;


import com.empirica.tourismagency.field.ReservationItem;
import com.empirica.tourismagency.field.Order;
import com.empirica.tourismagency.field.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface ReservationItemRepository extends CrudRepository<ReservationItem, Long>{
	List<ReservationItem> findByReservation(Reservation reservation);

	List<ReservationItem> findByOrder(Order order);

}
