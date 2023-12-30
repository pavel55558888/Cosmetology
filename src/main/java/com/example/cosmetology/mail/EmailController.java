package com.example.cosmetology.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;
    @GetMapping("/mail")
    public String mail(){
        EmailServiceImpl emailService = new EmailServiceImpl(javaMailSender);
        emailService.sendSimpleMessage("cfxb","cxb","xv");
        return "redirect:/";
    }
}
