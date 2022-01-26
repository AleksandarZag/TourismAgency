package com.empirica.tourismagency.repository;

import com.empirica.tourismagency.field.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
