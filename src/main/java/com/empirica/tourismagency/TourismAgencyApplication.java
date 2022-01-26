package com.empirica.tourismagency;

import com.empirica.tourismagency.field.User;
import com.empirica.tourismagency.field.security.Role;
import com.empirica.tourismagency.field.security.UserRole;
import com.empirica.tourismagency.maintenance.UserMaintenance;
import com.empirica.tourismagency.applicability.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@ComponentScan
public class TourismAgencyApplication  implements CommandLineRunner {
    @Autowired
    private UserMaintenance userMaintenance;

    public static void main(String[] args) {
        SpringApplication.run(TourismAgencyApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user1.setEmail("admin@gmail.com");
        Set<UserRole> userRoles = new HashSet<>();
        Role role1= new Role();
        role1.setRoleId(0);
        role1.setName("ROLE_ADMIN");
        userRoles.add(new UserRole(user1, role1));

        userMaintenance.createUser(user1, userRoles);

        User user2 = new User();
        user2.setFirstName("Aleksandar");
        user2.setLastName("Zaganovic");
        user2.setUsername("a");
        user2.setPassword(SecurityUtility.passwordEncoder().encode("p"));
        user2.setEmail("aleksandarzaganovic5@gmail.com");
        Set<UserRole> userRoles2 = new HashSet<>();
        Role role2 = new Role();
        role2.setRoleId(2);
        role2.setName("ROLE_USER");
        userRoles2.add(new UserRole(user2, role2));
        userMaintenance.createUser(user2, userRoles2);
    }

}
