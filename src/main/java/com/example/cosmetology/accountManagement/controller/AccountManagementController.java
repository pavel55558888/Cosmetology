package com.example.cosmetology.accountManagement.controller;

import com.example.cosmetology.authorization.model.Role;
import com.example.cosmetology.authorization.model.User;
import com.example.cosmetology.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class AccountManagementController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/newuser")
    public String newUser(@RequestParam(value = "error",defaultValue = "",required = false) String error, Model model){
        if (error.equals("username")){
            model.addAttribute("error","Данный логин занят!");
        } else if (error.equals("min")) {
            model.addAttribute("error","Какое-то из полей слишком слишком короткое!");
        } else if (error.equals("max")) {
            model.addAttribute("error","Какое-то из полей слишком слишком длинное!");
        } else if (error.equals("email")){
            model.addAttribute("error","Почта уже занята, расторгните договор или используйте другую почту");
        } else if (error.equals("emailNone")) {
            model.addAttribute("error2","Данная почта не зарегистрирована");
        }
        return "account-management/new-user";
    }

    @PostMapping("/newuser/add")
    public String newUserAdd(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String role){
        if (userRepo.findByUsername(username) != null) {
            return "redirect:/newuser?error=username";
        } else if (userRepo.findByMail(email) != null){
            return "redirect:/newuser?error=email";
        }else if(password.length()<=5 || username.length() <=5 || email.length() <=5){
            return "redirect:/newuser?error=min";
        } else if (username.length() >= 250 || email.length() >= 250 || password.length() >=250) {
            return "redirect:/newuser?error=max";
        }else{
            User user;
            password = passwordEncoder.encode(password);
            if (role.toString().equals("USER")){
                user = new User(username, email, password, true, Collections.singleton(Role.USER));
            }else if (role.equals("EMPLOYEE")) {
                user = new User(username, email, password, true, Collections.singleton(Role.EMPLOYEE));
            }else if (role.equals("ADMIN")) {
                user = new User(username, email, password, true, Collections.singleton(Role.ADMIN));
            } else if (role.equals("DEVELOPER")) {
                user = new User(username, email, password, true, Collections.singleton(Role.DEVELOPER));
            }else{
                user = new User(username, email, password, true, Collections.singleton(Role.USER));
            }
            userRepo.save(user);
            return "redirect:/";
        }
    }

    @PostMapping("/userdelete")
    public String deleteUser(@RequestParam String email){
        if (userRepo.findByMail(email) == null) {
            return "redirect:/newuser?error=emailNone";
        }else{
            User user = userRepo.findByMail(email);
            userRepo.delete(user);
            return "redirect:/";
        }
    }
}
