package com.example.cosmetology.monthly_report.service.impl;

import com.example.cosmetology.models.Consumables;
import com.example.cosmetology.models.DailyProfit;
import com.example.cosmetology.models.Orders;
import com.example.cosmetology.monthly_report.model.MonthlyReport;
import com.example.cosmetology.monthly_report.service.MonthlyReportService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MonthlyReportImpl implements MonthlyReportService {
    @Override
    public List<Orders> selectOrders() {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Orders.class).buildSessionFactory();

        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Orders> list = session.createQuery("from Orders").getResultList();
        session.getTransaction().commit();
        return list;
    }

    @Override
    public List<Consumables> selectPersonOrders() {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Consumables.class).buildSessionFactory();

        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Consumables> list = session.createQuery("from Consumables").getResultList();
        session.getTransaction().commit();

        return list;
    }

    @Override
    public List<DailyProfit> selectDailyProfit() {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(DailyProfit.class).buildSessionFactory();

        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<DailyProfit> list = session.createQuery("from DailyProfit").getResultList();
        session.getTransaction().commit();
        return list;
    }

    @Override
    public double filterOrder() {
        List<Orders> listOrders = selectOrders();

        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();

        double sumOrder = listOrders.stream()
                .filter(obj -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(obj.getCurrenDate(), formatter);
                    return date.getMonth() == currentMonth;
                })
                .mapToDouble(obj -> Double.parseDouble(obj.getPurchase_price()))
                .sum();

        return sumOrder;
    }

    @Override
    public double filterOrderPersonal() {
        List<Consumables> consumablesList = selectPersonOrders();

        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();

        double sumOrderPersonal = consumablesList.stream()
                .filter(obj -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(obj.getCurrentDate(), formatter);
                    return date.getMonth() == currentMonth;
                })
                .mapToDouble(obj -> Double.parseDouble(obj.getPurchase_price()))
                .sum();

        return sumOrderPersonal;
    }

    @Override
    public double filterDailyProfit() {
        List<DailyProfit> dailyProfitList = selectDailyProfit();

        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();

        double dailyProfitSum = dailyProfitList.stream()
                .filter(obj -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(obj.getDate(), formatter);
                    return date.getMonth() == currentMonth;
                })
                .mapToDouble(obj -> Double.parseDouble(obj.getProfit()))
                .sum();

        return dailyProfitSum;
    }

    @Override
    public void insertMothlyReport() {
        LocalDate currentDate = LocalDate.now();

        double netProfit = filterDailyProfit()+filterOrder()+filterOrderPersonal();


        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(MonthlyReport.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            MonthlyReport monthlyReport = new MonthlyReport(currentDate.toString(), String.valueOf(filterOrder()), String.valueOf(filterOrderPersonal()),"no", "no" , String.valueOf(netProfit), String.valueOf(filterDailyProfit()));
            session.beginTransaction();
            session.persist(monthlyReport);
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }
}
