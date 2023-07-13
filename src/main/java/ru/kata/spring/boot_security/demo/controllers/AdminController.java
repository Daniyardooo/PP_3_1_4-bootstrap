package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

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
        return "user";
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



    @DeleteMapping("/delete")
    public String removeUserById(User user) {
        userServiceImpl.deleteUserById(user.getId());
        return "redirect:/admin/users";

    }


    @PostMapping("/new")
    public String createUser(User user){

        User userFromDB = userServiceImpl.findByUsername(user.getUsername());
        if (userFromDB == user){
            throw new RuntimeException("User already exist");
        }
        userServiceImpl.saveUser(user);
        return "redirect:/admin/users";
    }






}
