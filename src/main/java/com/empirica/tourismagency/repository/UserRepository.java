package com.empirica.tourismagency.repository;

import com.empirica.tourismagency.field.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);

	User findByEmail(String email);
}
