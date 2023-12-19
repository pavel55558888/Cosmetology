package com.example.cosmetology.controllers;

import com.example.cosmetology.models.Orders;
import com.example.cosmetology.repository.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class OrdersController {
    @Autowired
    OrdersRepo ordersRepo;

    @GetMapping("/orders")
    public String orders(Model model){
        List<Orders> orders = ordersRepo.findAll();
        Collections.reverse(orders);
        model.addAttribute("orders", orders);
        return "orders/orders";
    }

    @GetMapping("/orders/{id}")
    public String fullOrder(@PathVariable(value = "id") long id, Model model){
        Orders order = ordersRepo.findById(id).orElse(new Orders());
        model.addAttribute("order", order);
        return "orders/full-order";
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable(value = "id") long id){
        ordersRepo.deleteById(id);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{id}/update")
    public String updateOrder(Model model, @PathVariable(value = "id") long id,@RequestParam(name = "error", defaultValue = "", required = false) String error){
        Orders order = ordersRepo.findById(id).orElse(new Orders());
        model.addAttribute("order", order);
        if (error.equals("null")){
            model.addAttribute("error","Заполните все поля");
        }
        return "orders/update-order";
    }

    @PostMapping("/orders/{id}/update/add")
    public String updateOrderAdd(@PathVariable(value = "id") long id,
                                 @RequestParam String name,
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
            return "redirect:/orders/{id}/update?error=null";
        }else{
            Orders orders = ordersRepo.findById(id).orElse(new Orders());
            orders.setName(name);
            orders.setPrice(price);
            orders.setExpiration_date(expiration_date);
            orders.setManufacturer(manufacturer);
            orders.setCountry_of_manufacture(country_of_manufacture);
            orders.setPurpose_of_use(purpose_of_use);
            orders.setImg(img);
            orders.setPurchase_price(purchase_price);
            orders.setQuantity(quantity);
            orders.setDescription(description);
            ordersRepo.save(orders);
        }
        return "redirect:/orders";
    }
}
