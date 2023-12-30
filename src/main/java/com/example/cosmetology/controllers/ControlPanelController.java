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


    @GetMapping("/controlpanel")
    public String controlPanel(){
        return "controlPanel/control-panel";
    }

    @GetMapping("/controlpanel/newarticles")
    public String newArticles(@RequestParam(value = "error", defaultValue = "", required = false) String error,Model model){
        if (error.equals("null")){
            model.addAttribute("error","Заполните все поля!");
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
        }else {
            Articles articles = new Articles(name, date, img, description);
            articlesRepo.save(articles);
        }

        return "redirect:/controlpanel";
    }

    @GetMapping("/controlpanel/neworder")
    public String newOrder(@RequestParam(value = "error", defaultValue = "", required = false) String error, Model model){
        if (error.equals("null")){
            model.addAttribute("error", "Заполните все поля!");
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
        }else{
            Orders orders = new Orders(name, price,expiration_date,manufacturer,country_of_manufacture,purpose_of_use,img,purchase_price,quantity,description);
            ordersRepo.save(orders);
        }
        return "redirect:/controlpanel";
    }

    @GetMapping("/controlpanel/updateindex")
    public String updateIndex(Model model, @RequestParam(value = "error",defaultValue = "", required = false) String error){
        AboutTheSalon aboutTheSalons = aboutTheSalonRepo.findById(1L).orElse(new AboutTheSalon());
        model.addAttribute("aboutTheSalon",aboutTheSalons);

        AddressOfTheSalon addressOfTheSalons = addressOfTheSalonRepo.findById(1L).orElse(new AddressOfTheSalon());
        model.addAttribute("addresofthesalon", addressOfTheSalons);

        if (error.equals("null")){
            model.addAttribute("error", "Заполните все поля!");
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
        }else{
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
        return "redirect:/controlpanel";
    }

    @GetMapping("/controlpanel/neworderpersonal")
    public String newOrderPersonal(@RequestParam(value = "error", defaultValue = "", required = false) String error, Model model){
        if (error.equals("null")){
            model.addAttribute("error", "Заполните все поля!");
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
        }
        Consumables consumables = new Consumables(name,purchase_price,expiration_date,manufacturer,img,in_stock);
        consumablesRepo.save(consumables);
        return "redirect:/controlpanel";
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
        }
        return "daily-profit/report";
    }

    @PostMapping("/controlpanel/dailyprofit/add")
    public String dailyProfitAdd(@RequestParam String date,
                                 @RequestParam String services,
                                 @RequestParam String profit){
        if (date.equals("") || services.equals("") || profit.equals("")){
            return "redirect:/controlpanel/dailyprofit?error=null";
        }else {
            DailyProfit dailyProfit = new DailyProfit(date, services, profit);
            dailyProfitRepo.save(dailyProfit);
        }
        return "redirect:/controlpanel";
    }

    @GetMapping("/controlpanel/newuser")
    public String newUser(@RequestParam(value = "error",defaultValue = "",required = false) String error, Model model){
        if (error.equals("username")){
            model.addAttribute("error","Данный логин занят!");
        } else if (error.equals("password")) {
            model.addAttribute("error","Пароль слишком легкий!");
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
        }else if(password.length()<=5){
            return "redirect:/controlpanel/newuser?error=password";
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
        return "redirect:/controlpanel";
    }
}
