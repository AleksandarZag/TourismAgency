package com.empirica.tourismagency.maintenance.implementation;

import com.empirica.tourismagency.field.Payment;
import com.empirica.tourismagency.field.UserPayment;
import com.empirica.tourismagency.maintenance.PaymentMaintenance;
import org.springframework.stereotype.Service;

@Service
public class PaymentMaintenanceImpl implements PaymentMaintenance {

    public Payment setByUserPayment(UserPayment userPayment, Payment payment){
        payment.setType(userPayment.getType());
        payment.setHolderName(userPayment.getHolderName());
        payment.setExpiryMonth(userPayment.getExpiryMonth());
        payment.setExpiryYear(userPayment.getExpiryYear());
        payment.setCvc(userPayment.getCvc());
        payment.setCardNumber(userPayment.getCardNumber());

        return payment;
    }

}
