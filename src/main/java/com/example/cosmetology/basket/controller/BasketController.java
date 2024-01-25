package com.example.cosmetology.basket.controller;

import com.example.cosmetology.basket.model.Basket;
import com.example.cosmetology.basket.service.impl.BasketServiceImpl;
import com.example.cosmetology.orders.model.Orders;
import com.example.cosmetology.repository.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BasketController {
    @Autowired
    OrdersRepo ordersRepo;
    private BasketServiceImpl basketServiceImpl = new BasketServiceImpl();
    @PostMapping("/orders/{id}/basket")
    public String addBasket(@PathVariable(value = "id") long id, RedirectAttributes redirectAttributes){
        Orders orders = ordersRepo.findById(id).orElse(new Orders());
        basketServiceImpl.basketAdd(orders.getName(),orders.getPrice(),orders.getManufacturer(),orders.getCountry_of_manufacture(),orders.getImg(),orders.getPurchase_price());
        basketServiceImpl.storedProcedures();

        redirectAttributes.addFlashAttribute("successMessage", "Товар успешно добавлен в корзину!");

        return "redirect:/orders";
    }
    @GetMapping("/basket")
    public String basket(Model model){
        List<Basket> list = basketServiceImpl.basketSelect();
        model.addAttribute("list",list);
        int sumPrice = list.stream().mapToInt(obj -> Integer.parseInt(obj.getPrice())).sum();
        model.addAttribute("sumPrice",sumPrice);
        int quantity = list.size();
        model.addAttribute("quantity",quantity);

        return "basket/basket";
    }

    @PostMapping("/basket/{id}/delete")
    public String basketDelete(@PathVariable(value = "id") long id){
        BasketServiceImpl basketServiceImpl = new BasketServiceImpl();
        basketServiceImpl.basketDelete(id);
        return "redirect:/basket";
    }

    @PostMapping("/basket/clear")
    public String basketClear(){
        BasketServiceImpl basketServiceImpl = new BasketServiceImpl();
        basketServiceImpl.clearBasket();
        return "redirect:/basket";
    }


}
