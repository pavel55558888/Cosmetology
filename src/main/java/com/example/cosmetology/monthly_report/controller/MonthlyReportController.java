package com.example.cosmetology.monthly_report.controller;

import com.example.cosmetology.monthly_report.model.MonthlyReport;
import com.example.cosmetology.monthly_report.service.impl.MonthlyReportImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class MonthlyReportController {
    @GetMapping("/monthly-report")
    public String monthlyReport(Model model){
        MonthlyReportImpl monthlyReport = new MonthlyReportImpl();
        List<MonthlyReport> monthlyReportList = monthlyReport.selectMonthlyReport();
        Collections.reverse(monthlyReportList);
        model.addAttribute("list", monthlyReportList);
        return "monthly-report/monthly-report";
    }

    @PostMapping("/monthly-report/start")
    public String monthlyReportStart(){
        MonthlyReportImpl monthlyReport = new MonthlyReportImpl();
        monthlyReport.insertMonthlyReport();
        return "redirect:/monthly-report";
    }
}
