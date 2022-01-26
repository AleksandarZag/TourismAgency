package com.empirica.tourismagency.maintenance.implementation;

import com.empirica.tourismagency.field.UserPayment;
import com.empirica.tourismagency.repository.UserPaymentRepository;
import com.empirica.tourismagency.maintenance.UserPaymentMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserPaymentMaintenanceImpl implements UserPaymentMaintenance {

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    public UserPayment findById(Long id) {
        return userPaymentRepository.findById(id)
                .orElse(null);
    }

    public void removeById(Long id) {
        userPaymentRepository.deleteById(id);
    }
}

