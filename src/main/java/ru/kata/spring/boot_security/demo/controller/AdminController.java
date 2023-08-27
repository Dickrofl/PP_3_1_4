package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
@Controller
public class AdminController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @GetMapping("/admin")
    public String getUsersPage(Principal principal, @ModelAttribute("user") User user,Model model) {
        String username = principal.getName();
        User userPrincipal = userService.findByUsername(username);
        model.addAttribute("principal", userService.getUserById(userPrincipal.getId()));
        model.addAttribute("user", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin";
    }
    @GetMapping("/newUser")
    public String getInfoForCreateUser(Model model){
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "newUser";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("newUser") User newUser) {
        userService.saveUser(newUser);
        return "redirect:/admin";
    }
    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }


}