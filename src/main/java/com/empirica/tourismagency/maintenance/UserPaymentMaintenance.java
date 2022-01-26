package com.empirica.tourismagency.maintenance;


import com.empirica.tourismagency.field.UserPayment;

public interface UserPaymentMaintenance {
	UserPayment findById(Long id);
	
	void removeById(Long id);
}
