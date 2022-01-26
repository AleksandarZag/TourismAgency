package com.empirica.tourismagency.maintenance;


import com.empirica.tourismagency.field.User;
import com.empirica.tourismagency.field.UserBilling;
import com.empirica.tourismagency.field.UserPayment;
import com.empirica.tourismagency.field.security.PasswordResetToken;
import com.empirica.tourismagency.field.security.UserRole;

import java.util.Set;



public interface UserMaintenance {

	PasswordResetToken getPasswordResetToken(final String token);

	void createPasswordResetTokenForUser(final User user, final String token);

	User findByUsername(String username);

	User findByEmail (String email);

	User findById(Long id);

	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);

	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);


	void setUserDefaultPayment(Long userPaymentId, User user);
	
}
