package com.empirica.tourismagency.maintenance;


import com.empirica.tourismagency.field.Payment;
import com.empirica.tourismagency.field.UserPayment;

public interface PaymentMaintenance {
    Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
