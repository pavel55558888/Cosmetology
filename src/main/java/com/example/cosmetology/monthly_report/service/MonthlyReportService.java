package com.example.cosmetology.monthly_report.service;

import com.example.cosmetology.models.Consumables;
import com.example.cosmetology.models.DailyProfit;
import com.example.cosmetology.models.Orders;

import java.util.List;

public interface MonthlyReportService {
    List<Orders> selectOrders();
    List<Consumables> selectPersonOrders();
    List<DailyProfit> selectDailyProfit();
    double filterOrder();
    double filterOrderPersonal();
    double filterDailyProfit();
    void insertMothlyReport();
}
