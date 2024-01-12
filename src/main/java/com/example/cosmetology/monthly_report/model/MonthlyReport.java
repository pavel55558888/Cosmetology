package com.example.cosmetology.monthly_report.model;

import jakarta.persistence.*;

@Entity
@Table(name = "monthly_report")
public class MonthlyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "date")
    private String date;
    @Column(name = "the_amount_of_purchase_of_goods_for_sale")
    private String theAmountOfPurchaseOfGoodsForSale;
    @Column(name = "the_amount_of_purchase_of_personal_goods")
    private String theAmountOfPurchaseOfPersonalGoods;
    @Column(name = "net_profit_from_orders")
    private String netProfitFromOrders;
    @Column(name = "number_of_orders")
    private String numberOfOrders;
    @Column(name = "net_profit")
    private String netProfit;
    @Column(name = "the_amount_of_daily_profit")
    private String theAmountOfDailyProfit;

    public MonthlyReport() {
    }

    public MonthlyReport(String date, String theAmountOfPurchaseOfGoodsForSale, String theAmountOfPurchaseOfPersonalGoods, String netProfitFromOrders, String numberOfOrders, String netProfit, String theAmountOfDailyProfit) {
        this.date = date;
        this.theAmountOfPurchaseOfGoodsForSale = theAmountOfPurchaseOfGoodsForSale;
        this.theAmountOfPurchaseOfPersonalGoods = theAmountOfPurchaseOfPersonalGoods;
        this.netProfitFromOrders = netProfitFromOrders;
        this.numberOfOrders = numberOfOrders;
        this.netProfit = netProfit;
        this.theAmountOfDailyProfit = theAmountOfDailyProfit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTheAmountOfPurchaseOfGoodsForSale() {
        return theAmountOfPurchaseOfGoodsForSale;
    }

    public void setTheAmountOfPurchaseOfGoodsForSale(String theAmountOfPurchaseOfGoodsForSale) {
        this.theAmountOfPurchaseOfGoodsForSale = theAmountOfPurchaseOfGoodsForSale;
    }

    public String getTheAmountOfPurchaseOfPersonalGoods() {
        return theAmountOfPurchaseOfPersonalGoods;
    }

    public void setTheAmountOfPurchaseOfPersonalGoods(String theAmountOfPurchaseOfPersonalGoods) {
        this.theAmountOfPurchaseOfPersonalGoods = theAmountOfPurchaseOfPersonalGoods;
    }

    public String getNetProfitFromOrders() {
        return netProfitFromOrders;
    }

    public void setNetProfitFromOrders(String netProfitFromOrders) {
        this.netProfitFromOrders = netProfitFromOrders;
    }

    public String getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(String numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }

    public String getTheAmountOfDailyProfit() {
        return theAmountOfDailyProfit;
    }

    public void setTheAmountOfDailyProfit(String theAmountOfDailyProfit) {
        this.theAmountOfDailyProfit = theAmountOfDailyProfit;
    }
}
