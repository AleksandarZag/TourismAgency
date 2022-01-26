package com.empirica.tourismagency.maintenance;


import com.empirica.tourismagency.field.*;

public interface OrderMaintenance {
    Order createOrder(Reservation reservation,
                      Payment payment,
                      User user);

    Order findOne(Long id);
}
