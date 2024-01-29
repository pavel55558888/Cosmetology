package com.example.cosmetology.orderspersonal.controller;

import com.example.cosmetology.orderspersonal.model.Consumables;
import com.example.cosmetology.repository.ConsumablesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class OrderPersonalController {
    @Autowired
    ConsumablesRepo consumablesRepo;

    @GetMapping("/controlpanel/orderpersonal/{id}")
    public String orderPersonalFull(@PathVariable(value = "id") long id, Model model){
        Consumables consumables = consumablesRepo.findById(id).orElse(new Consumables());
        model.addAttribute("order",consumables);
        return "order-personal/order-personal-full";
    }

    @PostMapping("/controlpanel/orderprsonal/{id}/delete")
    public String orderPersonalDelete(@PathVariable(value = "id") long id){
        consumablesRepo.deleteById(id);
        return "redirect:/controlpanel/orderpersonal";
    }

    @GetMapping("/controlpanel/orderpersonal/{id}/update")
    public String orderPersonalUpdate(@PathVariable(value = "id") long id,Model model,
                                      @RequestParam(value = "error", defaultValue = "", required = false) String error){
        Consumables consumables = consumablesRepo.findById(id).orElse(new Consumables());
        model.addAttribute("order", consumables);
        if (error.equals("null")){
            model.addAttribute("error", "Заполните все поля!");
        }else if (error.equals("max")){
            model.addAttribute("error", "Какое-то из полей слишком длинное!");
        }
        return "order-personal/order-personal-update";
    }

    @PostMapping("/controlpanel/orderpersonal/{id}/update/add")
    public String orderPersonalUpdateAdd(@PathVariable(value = "id") long id,
                                         @RequestParam String name,
                                         @RequestParam String purchase_price,
                                         @RequestParam String expiration_date,
                                         @RequestParam String manufacturer,
                                         @RequestParam String img,
                                         @RequestParam String in_stock){
        if (name.equals("") || purchase_price.equals("") || expiration_date.equals("") || manufacturer.equals("") || img.equals("") || in_stock.equals("")){
            return "redirect:/controlpanel/orderpersonal/{id}/update?error=null";
        } else if (name.length() >= 250 || purchase_price.length() >= 250 || expiration_date.length() >= 250 || manufacturer.length() >= 250
                || img.length() >= 250 || in_stock.length() >= 250){
            return "redirect:/controlpanel/orderpersonal/{id}/update?error=max";
        } else {
            Consumables consumables = consumablesRepo.findById(id).orElse(new Consumables());
            consumables.setName(name);
            consumables.setPurchase_price(purchase_price);
            consumables.setExpiration_date(expiration_date);
            consumables.setManufacturer(manufacturer);
            consumables.setImg(img);
            consumables.setIn_stock(in_stock);
            consumablesRepo.save(consumables);
        }
        return "redirect:/controlpanel/orderpersonal";
    }

}
