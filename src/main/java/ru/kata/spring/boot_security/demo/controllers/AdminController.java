package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUsers(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("users", userService.getAllUsers());

        return "allUsers";
    }

    @GetMapping("/{id}")
    public String getUpdateUserForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        return "update";
    }

    @GetMapping("/new")
    public String getNewUserForm(@ModelAttribute("user") User user) {
        return "new";
    }

    @PatchMapping("/{id}")
    public String updateUserById(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                 @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "update";
        if (userService.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username", "Имя пользователя уже существует");
            return "update";
        }
        userService.updateUserById(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{id}")
    public String removeUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";

    }


    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "new";
        User userFromDB = userService.findByUsername(user.getUsername());
        if (userFromDB != null) {
            bindingResult.rejectValue("username", "error.username", "Имя пользователя уже существует");
            return "new";
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }
}
