package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
        UserService userService = context.getBean(UserService.class);

        RoleDao roleDao = context.getBean(RoleDao.class);

        Role role1 = new Role(1L, "ROLE_USER");
        Role role2 = new Role(2L, "ROLE_ADMIN");

        roleDao.save(role1);
        roleDao.save(role2);
        Set<Role> roles = new HashSet<>();
        roles.add(role2);
        roles.add(role1);

        User user = new User("Daniyardo", "123456",
                "danhas@mail.ru", roles);
        userService.saveUser(user);

    }


}
