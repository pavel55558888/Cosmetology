package com.example.cosmetology.authorization.controller;

import com.example.cosmetology.authorization.model.Role;
import com.example.cosmetology.authorization.model.User;
import com.example.cosmetology.mail.config.MailSender;
import com.example.cosmetology.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SessionAttributes({"code","user"})
@Controller
public class AuthorizationController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSender mailSender;

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
    public String reg(@RequestParam(value = "error", defaultValue = "", required = false) String error, Model model, HttpSession session){
        if (error.equals("username")){
            model.addAttribute("error","Данный логин занят!");
        } else if (error.equals("usernameMin")) {
            model.addAttribute("error","Логин слишком легкий!");
        } else if (error.equals("usernameMax")) {
            model.addAttribute("error","Логин слишком длинный!");
        } else if (error.equals("mailMax")) {
            model.addAttribute("error","Почта слишком длинная!");
        } else if (error.equals("mail")) {
            model.addAttribute("error","Неверный формат почты!");
        }else if (error.equals("passwordMin")) {
            model.addAttribute("error","Пароль слишком легкий!");
        } else if (error.equals("passwordMax")) {
            model.addAttribute("error", "Пароль слишком длинный!");
        } else if (error.equals("agreement")) {
            model.addAttribute("error", "Необходимо согласие на обработку данных");
        }else if (error.equals("emailNotNull")) {
            model.addAttribute("error", "Данная почта занята");
        }
        return "login/reg";
    }

    @PostMapping("/reg/add")
    public String regAdd(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam(value = "agreement", required = false) String agreement){
        String regex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (userRepo.findByUsername(username) != null) {
            return "redirect:/reg?error=username";
        } else if (userRepo.findByMail(email) != null){
            return "redirect:/reg?error=emailNotNull";
        } else if(password.length()<=5){
            return "redirect:/reg?error=passwordMin";
        } else if (username.length()<=5) {
            return "redirect:/reg?error=usernameMin";
        } else if (username.length()>=250) {
            return "redirect:/reg?error=usernameMax";
        } else if(password.length()>=50){
            return "redirect:/reg?error=passwordMax";
        } else if(email.length()>=250){
            return "redirect:/reg?error=mailMax";
        } else if (!matcher.find()) {
            return "redirect:/reg?error=mail";
        } else if (agreement == null) {
        return "redirect:/reg?error=agreement";
        }

        password = passwordEncoder.encode(password);
        User user = new User(username, email, password, true, Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/recovery")
    public String recovery(@RequestParam(value = "error", defaultValue = "", required = false) String error, Model model){
        if (error.equals("emailMin")){
            model.addAttribute("error", "Введите e-mail!");
        } else if (error.equals("emailNull")){
            model.addAttribute("error", "Данный e-mail не найден!");
        }
        return "login/recovery";
    }

    @PostMapping("/recovery")
    public String recoveryPost(@RequestParam String email, HttpSession session){
        if (userRepo.findByMail(email) == null){
            return "redirect:/recovery?error=emailNull";
        }else {
            User user = userRepo.findByMail(email);
            session.setAttribute("user", user);

            if (email.equals("")){
                return "redirect:/recovery?error=emailMin";
            } else if (user.getMail().equals(email)){
                String code = generateRandomPassword();
                session.setAttribute("code", code);

                String subject = "Восстановление пороля на сайте \"Отражение\" ";
                String message = "Для восстановления пароля используйте код:   " + code + "\n\n\n" +
                        "Чтобы восстановить доступ к вашей учетной записи на сайте \"Отражение\",  выполните следующие шаги: \n\n" +
                        "1.Вернитесь на сайт \n" +
                        "2.Введите код и нажмите подтверждающую кнопку \n" +
                        "3.Введите новый пароль и подтвердите действие \n\n\n" +
                        "Если у вас возникли какие-либо вопросы или проблемы,  пожалуйста,  свяжитесь с нами по адресу support@отражение.com \n\n\n" +
                        "Не сообщайте никому код!";
                mailSender.send(email, subject, message);
            }else{
                return "redirect:/recovery?error=emailNull";
            }
        }
        return "redirect:/recovery/code";
    }

    public static String generateRandomPassword()
    {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    @GetMapping("/recovery/code")
    public String recoveryCode(@RequestParam(value = "error", defaultValue = "", required = false) String error, Model model){
        if (error.equals("code")){
            model.addAttribute("error", "Неверный код!");
        }
        return "login/recovery-code";
    }

    @PostMapping("/recovery/code")
    public String recoveryCode(@RequestParam String code,HttpSession session){
        String sessionCode = (String) session.getAttribute("code");
        if (!sessionCode.equals(code)){
            return "redirect:/recovery/code?error=code";
        }
        return "redirect:/recovery/pass";
    }

    @GetMapping("/recovery/pass")
    public String recoveryPass(@RequestParam(value = "error", defaultValue = "", required = false) String error, Model model){
        if (error.equals("min")){
            model.addAttribute("error", "Пароль слишком маленький!");
        } else if(error.equals("max")){
            model.addAttribute("error", "Пароль слишком большой!");
        } else if(error.equals("notEquals")){
            model.addAttribute("error", "Парольи разные!");
        }
        return "login/recovery-pass";
    }

    @PostMapping("/recovery/pass")
    public String recoveryPass(@RequestParam String pass1,
                               @RequestParam String pass2,
                               RedirectAttributes redirectAttributes,
                               HttpSession session){
        if (pass1.length() <= 5 || pass2.length() <= 5){
            return "redirect:/recovery/pass?error=min";
        } else if(pass1.length() >=50 || pass2.length() >=50){
            return "redirect:/recovery/pass?error=max";
        } else if(!pass1.equals(pass2)){
            return "redirect:/recovery/pass?error=notEquals";
        }

        User user = (User) session.getAttribute("user");

        user.setPassword(passwordEncoder.encode(pass2));
        userRepo.save(user);

        redirectAttributes.addFlashAttribute("recovery", "Пароль успешно создан!");

        session.invalidate();
        return "redirect:/login";
    }



}


