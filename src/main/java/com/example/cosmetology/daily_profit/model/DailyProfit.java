package com.example.cosmetology.daily_profit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "daily_profit")
public class DailyProfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "date")
    private String date;
    @Column(name = "services", length = 1000)
    private String services;
    @Column(name = "profit")
    private String profit;
    @Column(name = "code")
    private String code;

    public DailyProfit(String date, String services, String profit, String code) {
        this.date = date;
        this.services = services;
        this.profit = profit;
        this.code = code;
    }

    public DailyProfit() {
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

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DailyProfit{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", services='" + services + '\'' +
                ", profit='" + profit + '\'' +
                '}';
    }
}
