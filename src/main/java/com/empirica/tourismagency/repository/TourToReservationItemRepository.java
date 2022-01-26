package com.empirica.tourismagency.repository;


import com.empirica.tourismagency.field.TourToReservationItem;
import com.empirica.tourismagency.field.ReservationItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TourToReservationItemRepository extends CrudRepository<TourToReservationItem,Long> {
  void deleteByReservationItem(ReservationItem reservationItem);
}
