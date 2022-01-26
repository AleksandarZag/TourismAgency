package com.empirica.tourismagency.maintenance.implementation;


import com.empirica.tourismagency.field.*;
import com.empirica.tourismagency.repository.OrderRepository;
import com.empirica.tourismagency.maintenance.ReservationItemMaintenance;
import com.empirica.tourismagency.maintenance.OrderMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class OrderMaintenanceImpl implements OrderMaintenance {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ReservationItemMaintenance reservationItemMaintenance;

   public synchronized Order createOrder(Reservation reservation,
                                         Payment payment,
                                         User user){
       Order order = new Order();
       order.setOrderStatus("created");
       order.setPayment(payment);

       List<ReservationItem> reservationItemList = reservationItemMaintenance.findByReservation(reservation);

       for(ReservationItem reservationItem : reservationItemList) {
           Tour tour = reservationItem.getTour();
           reservationItem.setOrder(order);
           tour.setQuantity(tour.getQuantity() - reservationItem.getQty());
       }
       order.setReservationItemList(reservationItemList);
       order.setOrderDate(Calendar.getInstance().getTime());
       order.setOrderTotal(reservation.getGrandTotal());
       payment.setOrder(order);
       order.setUser(user);
       order = orderRepository.save(order);

       return order;
   }

    public Order findOne(Long id) {
        return orderRepository.findById(id)
                .orElse(null);
    }
}
