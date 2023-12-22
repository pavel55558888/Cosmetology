package com.example.cosmetology.models;

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
    @Column(name = "services")
    private String services;
    @Column(name = "profit")
    private String profit;

    public DailyProfit(String date, String services, String profit) {
        this.date = date;
        this.services = services;
        this.profit = profit;
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
