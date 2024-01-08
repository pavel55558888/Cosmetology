package com.example.cosmetology.expired_product.controller;

import com.example.cosmetology.expired_product.service.impl.ExpiredProductImpl;
import com.example.cosmetology.models.Consumables;
import com.example.cosmetology.models.Orders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ExpiredProductController {
    @GetMapping("/expired-product")
    public String expiredProduct(Model model){
        ExpiredProductImpl expiredProduct = new ExpiredProductImpl();
        List<Orders> list = expiredProduct.SelectOrders();
        model.addAttribute("list",list);
        return "expired-product/expired-product";
    }
    @GetMapping("/expired-product-personal")
    public String expiredProductPersonal(Model model){
        ExpiredProductImpl expiredProduct = new ExpiredProductImpl();
        List<Consumables> list = expiredProduct.SelectPersonalOrders();
        model.addAttribute("list",list);
        return "expired-product/expired-product-personal";
    }
}
