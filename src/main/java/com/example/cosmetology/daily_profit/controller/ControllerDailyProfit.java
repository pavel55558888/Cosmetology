package com.example.cosmetology.daily_profit.controller;

import com.example.cosmetology.daily_profit.model.DailyProfit;
import com.example.cosmetology.monthly_report.service.impl.MonthlyReportImpl;
import com.example.cosmetology.orderspersonal.model.Consumables;
import com.example.cosmetology.repository.ConsumablesRepo;
import com.example.cosmetology.repository.DailyProfitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class ControllerDailyProfit {
    @Autowired
    private ConsumablesRepo consumablesRepo;
    @Autowired
    private DailyProfitRepo dailyProfitRepo;
    @GetMapping("/dailyprofit")
    public String dailyProfit(Model model, @RequestParam(value = "error" , defaultValue = "" ,required = false) String error){
        List<DailyProfit> list = dailyProfitRepo.findAll();
        Collections.reverse(list);

        List<Consumables> orders = consumablesRepo.findAll();
        model.addAttribute("orders", orders);

        model.addAttribute("list",list);
        if (error.equals("null")) {
            model.addAttribute("error", "Заполните все поля!");
        } else if(error.equals("max")) {
            model.addAttribute("error", "Какое-то из полей слишком длинное");
        }
        return "daily-profit/report";
    }

    @PostMapping("/dailyprofit/add")
    public String dailyProfitAdd(@RequestParam String date,
                                 @RequestParam String services,
                                 @RequestParam String profit,
                                 @RequestParam String ordersExit){
        if (date.equals("") || services.equals("") || profit.equals("")){
            return "redirect:/dailyprofit?error=null";
        } else if (date.length() >= 250 || profit.length() >=250 || services.length() >= 1000) {
            return "redirect:/dailyprofit?error=max";
        } else {
            Consumables consumables = consumablesRepo.findById(Long.valueOf(ordersExit)).orElse(new Consumables());
            if (ordersExit.equals("Выберите затраченый товар")){
                ordersExit = null;
            }
            if (ordersExit != null){
                int inStock = Integer.parseInt(consumables.getIn_stock()) - 1;
                if (inStock >= 1){
                    consumables.setIn_stock(String.valueOf(inStock));
                    consumablesRepo.save(consumables);
                }else{
                    consumablesRepo.deleteById(Long.valueOf(ordersExit));
                }
            }
            DailyProfit dailyProfit = new DailyProfit(date, services, profit, consumables.getCode());
            dailyProfitRepo.save(dailyProfit);

            MonthlyReportImpl monthlyReport = new MonthlyReportImpl();
            if (monthlyReport.checkingForTheFirstNumber()){
                monthlyReport.selectMonthlyReportСheck();
            }
        }
        return "redirect:/";
    }
}
