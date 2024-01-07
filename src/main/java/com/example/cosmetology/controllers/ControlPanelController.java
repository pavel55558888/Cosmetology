package com.example.cosmetology.controllers;

import com.example.cosmetology.models.AboutTheSalon;
import com.example.cosmetology.models.AddressOfTheSalon;
import com.example.cosmetology.models.Articles;
import com.example.cosmetology.models.*;
import com.example.cosmetology.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class ControlPanelController {
    @Autowired
    ArticlesRepo articlesRepo;
    @Autowired
    OrdersRepo ordersRepo;
    @Autowired
    AddressOfTheSalonRepo addressOfTheSalonRepo;
    @Autowired
    AboutTheSalonRepo aboutTheSalonRepo;
    @Autowired
    ConsumablesRepo consumablesRepo;
    @Autowired
    DailyProfitRepo dailyProfitRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/controlpanel/newarticles")
    public String newArticles(@RequestParam(value = "error", defaultValue = "", required = false) String error,Model model){
        if (error.equals("null")){
            model.addAttribute("error","Заполните все поля!");
        } else if (error.equals("name")) {
            model.addAttribute("error","Название слишком длинное!");
        } else if (error.equals("date")) {
            model.addAttribute("error","Дата слишком длинная!");
        } else if (error.equals("img")) {
            model.addAttribute("error","Ссылка на фото слишком длинная!");
        } else if (error.equals("description")) {
            model.addAttribute("error","Описание сликом длинное!");
        }
        return "controlpanel/new-articles";
    }

    @PostMapping("/controlpanel/newarticles/add")
    public String articlesAdd(@RequestParam String name,
                              @RequestParam String date,
                              @RequestParam String img,
                              @RequestParam String description){

        if (name.equals("") || date.equals("") || img.equals("") || description.equals("")){
            return "redirect:/controlpanel/newarticles?error=null";
        } else if (name.length() >= 250) {
            return "redirect:/controlpanel/newarticles?error=name";
        } else if (date.length() >= 250) {
            return "redirect:/controlpanel/newarticles?error=date";
        } else if (img.length() >= 250) {
            return "redirect:/controlpanel/newarticles?error=img";
        } else if (description.length() >= 20000) {
            return "redirect:/controlpanel/newarticles?error=description";
        } else {
            Articles articles = new Articles(name, date, img, description);
            articlesRepo.save(articles);
        }

        return "redirect:/";
    }

    @GetMapping("/controlpanel/neworder")
    public String newOrder(@RequestParam(value = "error", defaultValue = "", required = false) String error, Model model){
        if (error.equals("null")){
            model.addAttribute("error", "Заполните все поля!");
        } else if (error.equals("max")) {
            model.addAttribute("error", "Какое-то поле слишком длинное!");
        } else if (error.equals("description")) {
            model.addAttribute("error", "Описание слишком длинное!");
        }
        return "controlpanel/new-order";
    }

    @PostMapping("/controlpanel/neworder/add")
    public String orderAdd(@RequestParam String name,
                           @RequestParam String price,
                           @RequestParam String expiration_date,
                           @RequestParam String manufacturer,
                           @RequestParam String country_of_manufacture,
                           @RequestParam String purpose_of_use,
                           @RequestParam String img,
                           @RequestParam String purchase_price,
                           @RequestParam String quantity,
                           @RequestParam String description){

        if(name.equals("") || price.equals("") || expiration_date.equals("") || manufacturer.equals("") || country_of_manufacture.equals("")
                || purpose_of_use.equals("") || img.equals("") || purchase_price.equals("") || quantity.equals("") || description.equals("")){
            return "redirect:/controlpanel/neworder?error=null";
        } else if (name.length() >= 250 || price.length() >= 250 || expiration_date.length() >= 250 || manufacturer.length() >= 250
                || country_of_manufacture.length() >= 250 || purchase_price.length() >= 250 || purpose_of_use.length() >= 250 || img.length() >= 250
                || quantity.length() >= 250 || description.length() >= 19000) {
            return "redirect:/controlpanel/neworder?error=max";
        } else{
            Orders orders = new Orders(name, price,expiration_date,manufacturer,country_of_manufacture,purpose_of_use,img,purchase_price,quantity,description);
            ordersRepo.save(orders);
        }
        return "redirect:/orders";
    }

    @GetMapping("/controlpanel/updateindex")
    public String updateIndex(Model model, @RequestParam(value = "error",defaultValue = "", required = false) String error){
        AboutTheSalon aboutTheSalons = aboutTheSalonRepo.findById(1L).orElse(new AboutTheSalon());
        model.addAttribute("aboutTheSalon",aboutTheSalons);

        AddressOfTheSalon addressOfTheSalons = addressOfTheSalonRepo.findById(1L).orElse(new AddressOfTheSalon());
        model.addAttribute("addresofthesalon", addressOfTheSalons);

        if (error.equals("null")){
            model.addAttribute("error", "Заполните все поля!");
        } else if (error.equals("max")){
            model.addAttribute("error", "Какое-то поле слишком длинное!");
        }

        return "controlpanel/update-index";
    }

    @PostMapping("/controlpanel/updateindex/add")
    public String updateIndexAdd(@RequestParam String activity,
                                 @RequestParam String history,
                                 @RequestParam String procedures,
                                 @RequestParam String img,
                                 @RequestParam String address){
        if (activity.equals("") || history.equals("") || procedures.equals("") || img.equals("") || address.equals("")){
            return "redirect:/controlpanel/updateindex?error=null";
        } else if (activity.length() >= 5000 || history. length() >= 5000 || procedures.length() >= 5000 || img.length() >= 250 || address.length() >= 250) {
            return "redirect:/controlpanel/updateindex?error=max";
        } else{
            AboutTheSalon aboutTheSalon = aboutTheSalonRepo.findById(1L).orElse(new AboutTheSalon());
            aboutTheSalon.setActivity(activity);
            aboutTheSalon.setHistory(history);
            aboutTheSalon.setProcedures(procedures);
            aboutTheSalonRepo.save(aboutTheSalon);

            AddressOfTheSalon addressOfTheSalon = addressOfTheSalonRepo.findById(1L).orElse(new AddressOfTheSalon());
            addressOfTheSalon.setImg(img);
            addressOfTheSalon.setAddress(address);
            addressOfTheSalonRepo.save(addressOfTheSalon);
        }
        return "redirect:/";
    }

    @GetMapping("/controlpanel/neworderpersonal")
    public String newOrderPersonal(@RequestParam(value = "error", defaultValue = "", required = false) String error, Model model){
        if (error.equals("null")){
            model.addAttribute("error", "Заполните все поля!");
        } else if (error.equals("max")){
            model.addAttribute("error", "Какое-то поле слишком длинное!");
        }
        return "controlpanel/new-order-personal";
    }

    @PostMapping("/controlpanel/neworderdpersonal/add")
    public String newOrderPersonalAdd(@RequestParam String name,
                                      @RequestParam String purchase_price,
                                      @RequestParam String expiration_date,
                                      @RequestParam String manufacturer,
                                      @RequestParam String img,
                                      @RequestParam String in_stock){
        if (name.equals("") || purchase_price.equals("") || expiration_date.equals("") || manufacturer.equals("") || img.equals("") || in_stock.equals("")){
            return "redirect:/controlpanel/neworderpersonal?error=null";
        } else if (name.length() >= 250 || purchase_price.length() >= 250 || expiration_date.length() >= 250 || manufacturer.length() >= 250
                || img.length() >= 250 || in_stock.length() >= 250){
            return "redirect:/controlpanel/neworderpersonal?error=max";
        }
        Consumables consumables = new Consumables(name,purchase_price,expiration_date,manufacturer,img,in_stock);
        consumablesRepo.save(consumables);
        return "redirect:/controlpanel/orderpersonal";
    }

    @GetMapping("/controlpanel/orderpersonal")
    public String orderPersonal(Model model){
        List<Consumables> list = consumablesRepo.findAll();
        model.addAttribute("list", list);

        return "order-personal/order-personal";
    }

    @GetMapping("/controlpanel/dailyprofit")
    public String dailyProfit(Model model, @RequestParam(value = "error" , defaultValue = "" ,required = false) String error){
        List<DailyProfit> list = dailyProfitRepo.findAll();
        Collections.reverse(list);
        model.addAttribute("list",list);
        if (error.equals("null")) {
            model.addAttribute("error", "Заполните все поля!");
        } else if(error.equals("max")) {
            model.addAttribute("error", "Какое-то из полей слишком длинное");
        }
        return "daily-profit/report";
    }

    @PostMapping("/controlpanel/dailyprofit/add")
    public String dailyProfitAdd(@RequestParam String date,
                                 @RequestParam String services,
                                 @RequestParam String profit){
        if (date.equals("") || services.equals("") || profit.equals("")){
            return "redirect:/controlpanel/dailyprofit?error=null";
        } else if (date.length() >= 250 || profit.length() >=250 || services.length() >= 1000) {
            return "redirect:/controlpanel/dailyprofit?error=max";
        } else {
            DailyProfit dailyProfit = new DailyProfit(date, services, profit);
            dailyProfitRepo.save(dailyProfit);
        }
        return "redirect:/";
    }

    @GetMapping("/controlpanel/newuser")
    public String newUser(@RequestParam(value = "error",defaultValue = "",required = false) String error, Model model){
        if (error.equals("username")){
            model.addAttribute("error","Данный логин занят!");
        } else if (error.equals("min")) {
            model.addAttribute("error","Какое-то из полей слишком слишком короткое!");
        } else if (error.equals("max")) {
            model.addAttribute("error","Какое-то из полей слишком слишком длинное!");
        }
        return "controlpanel/new-user";
    }

    @PostMapping("/controlpanel/newuser/add")
    public String newUserAdd(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String role){
        if (userRepo.findByUsername(username) != null) {
            return "redirect:/controlpanel/newuser?error=username";
        }else if(password.length()<=5 || username.length() <=5 || email.length() <=5){
            return "redirect:/controlpanel/newuser?error=min";
        } else if (username.length() >= 250 || email.length() >= 250 || password.length() >=250) {
            return "redirect:/controlpanel/newuser?error=max";
        }
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
        System.out.println(role);
        userRepo.save(user);
        return "redirect:/";
    }
}
