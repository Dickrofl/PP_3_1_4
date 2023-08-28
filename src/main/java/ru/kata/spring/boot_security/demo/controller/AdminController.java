package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
@Controller
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String getUsersPage(Principal principal, @ModelAttribute("user") User user,Model model) {
        String username = principal.getName();
        User userPrincipal = userService.findByUsername(username);
        model.addAttribute("principal", userService.getUserById(userPrincipal.getId()));
        model.addAttribute("user", userService.getAllUsers());
        model.addAttribute("roles", roleService.getListRoles());
        return "admin";
    }
    @GetMapping("/newUser")
    public String getInfoForCreateUser(Model model){
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getListRoles());
        return "newUser";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("newUser") User newUser) {
        String hashedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);
        userService.saveUser(newUser);
        return "redirect:/admin";
    }
    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userService.updateUser(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }


}