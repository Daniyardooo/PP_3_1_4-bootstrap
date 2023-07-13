package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserById(Long userId);

    void updateUserById(Long id, User updatedUser);

    void saveUser(User user);

    List<User> getAllUsers();

    void deleteUserById(Long userId);

    Optional<User> findByUsername(String username);

}
