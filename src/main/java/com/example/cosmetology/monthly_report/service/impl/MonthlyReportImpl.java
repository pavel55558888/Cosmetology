package com.example.cosmetology.monthly_report.service.impl;

import com.example.cosmetology.orderspersonal.model.Consumables;
import com.example.cosmetology.daily_profit.model.DailyProfit;
import com.example.cosmetology.orders.model.Orders;
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
        List<Orders> list;

        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Orders.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            list = session.createQuery("from Orders").getResultList();
            session.getTransaction().commit();
        }finally {
            factory.close();
        }

        return list;
    }

    @Override
    public List<Consumables> selectPersonOrders() {
        List<Consumables> list;

        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Consumables.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            list = session.createQuery("from Consumables").getResultList();
            session.getTransaction().commit();
        }finally {
            factory.close();
        }

        return list;
    }

    @Override
    public List<DailyProfit> selectDailyProfit() {
        List<DailyProfit> list;

        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(DailyProfit.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            list = session.createQuery("from DailyProfit").getResultList();
            session.getTransaction().commit();
        }finally {
            factory.close();
        }

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
    public void insertMonthlyReport() {
        LocalDate currentDate = LocalDate.now();

        double netProfit = filterDailyProfit()-filterOrder()-filterOrderPersonal();

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

    @Override
    public boolean checkingForTheFirstNumber() {
        LocalDate currentDate = LocalDate.now();
        int lastDayOfMonth = currentDate.lengthOfMonth();
        return currentDate.getDayOfMonth() == lastDayOfMonth;
    }

    @Override
    public void selectMonthlyReportСheck() {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(MonthlyReport.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            List<MonthlyReport> list = session.createQuery("from MonthlyReport where date = :date").setParameter("date", LocalDate.now().toString()).getResultList();
            if (list.isEmpty()) {
                insertMonthlyReport();
            }
            session.getTransaction().commit();
        }finally {
            factory.close();
        }
    }

    @Override
    public List<MonthlyReport> selectMonthlyReport() {
        List<MonthlyReport> list;

        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(MonthlyReport.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            list = session.createQuery("from MonthlyReport").getResultList();

            session.getTransaction().commit();
        }finally {
            factory.close();
        }

        return list;
    }
}
