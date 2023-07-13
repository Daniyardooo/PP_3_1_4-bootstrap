package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
        User user = userServiceImpl.findByUsername(principalName);
        int nextUserId = userServiceImpl.getAllUsers().size()+1;
        model.addAttribute("user", user);
        model.addAttribute("nextUserId", nextUserId);
        return "allUsers";
    }


    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(Long id){
        return userServiceImpl.findOne(id);
    }

    @GetMapping("/{id}")
    public String getUpdateUserForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userServiceImpl.findUserById(id));
        model.addAttribute("users", userServiceImpl.getAllUsers());

        return "allUsers";
    }

    @GetMapping("/new")
    public String getNewUserForm(@ModelAttribute("user") User user) {
        return "new";
    }

    @PatchMapping("/update")
    public String updateUserById(User user, Principal principal, RedirectAttributes redirectAttributes) {
        if(principal.getName().equals(userServiceImpl.findUserById(user.getId()).getUsername()) && !principal.getName().equals(user.getUsername())){
                userServiceImpl.updateUserById(user.getId(), user);
            redirectAttributes.addFlashAttribute("message", "Пожалуйста, выполните повторную авторизацию.");

            return "redirect:/login";

        }
        userServiceImpl.updateUserById(user.getId(), user);
        return "redirect:/admin/users";
    }



    @PatchMapping("/delete")
    public String removeUserById(User user) {
        userServiceImpl.deleteUserById(user.getId());
        return "redirect:/admin/users";

    }


    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "allUsers";
        User userFromDB = userServiceImpl.findByUsername(user.getUsername());
        if (userFromDB != null) {
            bindingResult.rejectValue("username", "error.username", "Имя пользователя уже существует");
            return "allUsers";
        }
        userServiceImpl.saveUser(user);
        return "redirect:/admin/users";
    }






}
