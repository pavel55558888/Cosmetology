package com.example.cosmetology.monthly_report.service;

import com.example.cosmetology.orderspersonal.model.Consumables;
import com.example.cosmetology.daily_profit.model.DailyProfit;
import com.example.cosmetology.orders.model.Orders;
import com.example.cosmetology.monthly_report.model.MonthlyReport;

import java.util.List;

public interface MonthlyReportService {
    List<Orders> selectOrders();
    List<Consumables> selectPersonOrders();
    List<DailyProfit> selectDailyProfit();
    double filterOrder();
    double filterOrderPersonal();
    double filterDailyProfit();
    void insertMonthlyReport();
    boolean checkingForTheFirstNumber();
    void selectMonthlyReportСheck();
    List<MonthlyReport> selectMonthlyReport();
}
