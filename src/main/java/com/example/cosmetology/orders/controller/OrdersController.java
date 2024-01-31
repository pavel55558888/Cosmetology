package com.example.cosmetology.orders.controller;

import com.example.cosmetology.basket.service.impl.BasketServiceImpl;
import com.example.cosmetology.orders.model.Orders;
import com.example.cosmetology.repository.OrdersRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Controller
public class OrdersController {
    @Autowired
    OrdersRepo ordersRepo;
    private List<Orders> orders;
    private List<Orders> ordersSorted;
    private List<Orders> searchResults;

    @GetMapping("/orders")
    public String orders(Model model){
        orders = ordersRepo.findAll();
        Collections.reverse(orders);
        model.addAttribute("ordersAdmin", orders);
        Set<Orders> UniqueSortingOrders = new HashSet<>(orders);
        ordersSorted = new ArrayList<>(UniqueSortingOrders);
        model.addAttribute("ordersUser", ordersSorted);
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
        Orders orders = ordersRepo.findById(id).orElse(new Orders());
        ordersRepo.deleteById(id);
        BasketServiceImpl basketServiceImpl = new BasketServiceImpl();
        basketServiceImpl.basketDeleteName(orders.getName());
        return "redirect:/orders";
    }

    @GetMapping("/orders/{id}/update")
    public String updateOrder(Model model, @PathVariable(value = "id") long id,@RequestParam(name = "error", defaultValue = "", required = false) String error){
        Orders order = ordersRepo.findById(id).orElse(new Orders());
        model.addAttribute("order", order);
        if (error.equals("null")){
            model.addAttribute("error","Заполните все поля!");
        }else if (error.equals("max")){
            model.addAttribute("error","Какое-то из полей слишком длинное!");
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
        }else if (name.length() >= 250 || price.length() >= 250 || expiration_date.length() >= 250 || manufacturer.length() >= 250
                || country_of_manufacture.length() >= 250 || purchase_price.length() >= 250 || purpose_of_use.length() >= 250 || img.length() >= 250
                || quantity.length() >= 250 || description.length() >= 18000) {
            return "redirect:/orders/{id}/update?error=max";
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

    @PostMapping("/orders/search/user")
    public String ordersSearchUser(@RequestParam String search){
        searchResults = searchObjects(ordersSorted, search);
        return "redirect:/orders/search/user";
    }

    @GetMapping("/orders/search/user")
    public String ordersSearchUser(Model model){
        model.addAttribute("ordersUser", searchResults);
        return "orders/orders";
    }

    @PostMapping("/orders/search/admin")
    public String ordersSearchAdmin(@RequestParam String search){
        searchResults = searchObjects(orders, search);
        return "redirect:/orders/search/admin";
    }
    @GetMapping("/orders/search/admin")
    public String ordersSearchAdmin(Model model){
        model.addAttribute("ordersAdmin", searchResults);
        return "orders/orders";
    }

    private static List<Orders> searchObjects(List<Orders> ordersList, String userQuery) {
        List<Orders> searchResults = new ArrayList<>();

        for (Orders order : ordersList) {
            String name = order.getName();
            String purposeOfUse = order.getPurpose_of_use();
            String manufacturer = order.getManufacturer();

            List<String> fieldsToSearch = Arrays.asList(name, purposeOfUse, manufacturer);

            String[] userQueryWords = userQuery.toLowerCase().split("\\s+");

            for (String field : fieldsToSearch) {
                if (field != null && containsAnyWord(field.toLowerCase(), userQueryWords)) {
                    searchResults.add(order);
                    break;
                }
            }
        }

        return searchResults;
    }

    private static boolean containsAnyWord(String haystack, String[] needles) {
        for (String needle : needles) {
            if (haystack.contains(needle)) {
                return true;
            }
        }
        return false;
    }
}
