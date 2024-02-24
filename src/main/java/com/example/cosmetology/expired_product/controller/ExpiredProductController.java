package com.example.cosmetology.expired_product.controller;

import com.example.cosmetology.expired_product.service.impl.ExpiredProductImpl;
import com.example.cosmetology.orderspersonal.model.Consumables;
import com.example.cosmetology.orders.model.Orders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ExpiredProductController {
    @GetMapping("/expired-product-6")
    public String orders(Model model){
        ExpiredProductImpl expiredProduct = new ExpiredProductImpl();
        List<Orders> list = expiredProduct.SelectOrders();
        model.addAttribute("list",list);
        return "expired-product/expired-product";
    }
    @GetMapping("/expired-product-personal-6")
    public String ordersPersonal(Model model){
        ExpiredProductImpl expiredProduct = new ExpiredProductImpl();
        List<Consumables> list = expiredProduct.SelectPersonalOrders();
        model.addAttribute("list",list);
        return "expired-product/expired-product-personal";
    }
    @GetMapping("/expired-product")
    public String ordersExpired(Model model){
        ExpiredProductImpl expiredProduct = new ExpiredProductImpl();
        List<Orders> list = expiredProduct.SelectOrdersExpired();
        model.addAttribute("list",list);
        return "expired-product/expired-product";
    }
    @GetMapping("/expired-product-personal")
    public String ordersPersonalExpired(Model model){
        ExpiredProductImpl expiredProduct = new ExpiredProductImpl();
        List<Consumables> list = expiredProduct.SelectPersonalOrdersExpired();
        model.addAttribute("list",list);
        return "expired-product/expired-product-personal";
    }
}
