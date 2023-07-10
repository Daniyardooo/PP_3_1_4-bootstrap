package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public User findUserById(Long userId) {
        Optional<User> userFromDb = userDao.findById(userId);
        return userFromDb.orElse(new User());
    }


    public void updateUserById(Long id, User updatedUser) {
        Optional<User> existingUser = userDao.findById(id);
        if (existingUser.isPresent()) {
            existingUser.get().setUsername(updatedUser.getUsername());
            existingUser.get().setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
            existingUser.get().setEmail(updatedUser.getEmail());
            userDao.save(existingUser.get());
        } else throw new RuntimeException("Update user error");
    }

    public void saveUser(User user) {
        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void deleteUserById(Long userId) {
        if (userDao.findById(userId).isPresent()) {
            userDao.deleteById(userId);
        }
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }


}
