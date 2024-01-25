package com.example.cosmetology.mail.controller;

import com.example.cosmetology.mail.config.MailSender;
import com.example.cosmetology.mail.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class MailController {
    @Autowired
    private MailSender mailSender;
    @GetMapping("/mail")
    public String mail(@RequestParam(value = "error",defaultValue = "",required = false) String error, Model model){
        if (error.equals("min")){
            model.addAttribute("error", "Заполните все поля верно!");
        } else if (error.equals("limit")){
            model.addAttribute("error", "Лимит отправки записей на сегодня исчерпан!");
        }
        return "mail/mail";
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String name,
                            @RequestParam String phone,
                            @RequestParam String date,
                            @RequestParam String time) {
        MailServiceImpl mailService = new MailServiceImpl();
        boolean tracking = mailService.tracking();

        if (!tracking){
            return "redirect:/mail?error=limit";
        } else if (name.length()<=5 || phone.length() <= 5 || date.length() <=5 || time.length() <= 2){
            return "redirect:/mail?error=min";
        }
        try {
            String emailTo = "p-vikulinpbb@yandex.ru";
            String subject = "Новая заявка на запись";
            String message = "ФИО: " + name + "\n"
                    + "Номер для связи: " + phone + "\n"
                    + "Желаемая дата: " + date + "\n"
                    + "Желаемое время: " + time + "\n";
            mailSender.send(emailTo, subject, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}