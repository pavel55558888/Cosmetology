package com.example.cosmetology.controllers;

import com.example.cosmetology.models.Role;
import com.example.cosmetology.models.User;
import com.example.cosmetology.repository.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Collections;

@Controller
public class AuthorizationController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String auth(){

        return "login/auth";
    }

    @GetMapping("/login-error")
    public String login(Model model) {
        model.addAttribute("error", "Неверные данные!");
        return "login/auth";
    }

    @GetMapping("/reg")
    public String reg(@RequestParam(value = "error", defaultValue = "", required = false) String error, Model model){
        if (error.equals("username")){
            model.addAttribute("error","Данный логин занят!");
        } else if (error.equals("usernameMin")) {
            model.addAttribute("error","Логин слишком легкий!");
        } else if (error.equals("usernameMax")) {
            model.addAttribute("error","Логин слишком длинный!");
        } else if (error.equals("passwordMin")) {
            model.addAttribute("error","Пароль слишком легкий!");
        } else if (error.equals("passwordMax")) {
            model.addAttribute("error","Пароль слишком длинный!");
        } else if (error.equals("emailMax")) {
            model.addAttribute("error","Почта слишком длинная!");
        }
        return "login/reg";
    }

    @PostMapping("/reg/add")
    public String regAdd(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String password){
        if (userRepo.findByUsername(username) != null) {
            return "redirect:/reg?error=username";
        }else if(password.length()<=5){
            return "redirect:/reg?error=passwordMin";
        } else if (username.length()<=5) {
            return "redirect:/reg?error=usernameMin";
        } else if (username.length()>=250) {
            return "redirect:/reg?error=usernameMax";
        } else if(password.length()>=250){
            return "redirect:/reg?error=passwordMax";
        } else if(email.length()>=250){
            return "redirect:/reg?error=mailMax";
        }
        password = passwordEncoder.encode(password);
        User user = new User(username, email, password, true, Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }

}
