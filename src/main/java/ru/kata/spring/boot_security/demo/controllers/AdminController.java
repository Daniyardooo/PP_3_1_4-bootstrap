package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("")
    public String getUsers(Model model, Principal principal) {
        model.addAttribute("users", userServiceImpl.getAllUsers());
        String principalName = principal.getName();
        User user = userServiceImpl.findByUsername(principalName).get();
        int nextUserId = userServiceImpl.getAllUsers().size() + 1;
        model.addAttribute("user", user);
        model.addAttribute("nextUserId", nextUserId);
        return "allUsers";
    }


    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(Long id) {
        return userServiceImpl.findUserById(id);
    }


    @PatchMapping("/update")
    public String updateUserById(User user, Principal principal) {
        if (principal.getName().equals(userServiceImpl.findUserById(user.getId()).getUsername()) && !principal.getName().equals(user.getUsername())) {
            userServiceImpl.updateUserById(user.getId(), user);
            return "redirect:/login";
        }
        userServiceImpl.updateUserById(user.getId(), user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete")
    public String removeUserById(User user) {
        userServiceImpl.deleteUserById(user.getId());
        return "redirect:/admin/users";
    }
    @PostMapping("/new")
    public String createUser(User user) {
        Optional<User> userFromDB = userServiceImpl.findByUsername(user.getUsername());
        if (userFromDB.isPresent()) {
            return "redirect:/admin/users?error=User already exists ";
        }
        userServiceImpl.saveUser(user);
        return "redirect:/admin/users";
    }
}


