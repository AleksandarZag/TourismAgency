package com.empirica.tourismagency.repository;

import com.empirica.tourismagency.field.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
