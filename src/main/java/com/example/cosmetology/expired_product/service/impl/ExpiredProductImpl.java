package com.example.cosmetology.expired_product.service.impl;

import com.example.cosmetology.expired_product.service.ExpiredProduct;
import com.example.cosmetology.orderspersonal.model.Consumables;
import com.example.cosmetology.orders.model.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.List;

public class ExpiredProductImpl implements ExpiredProduct {
    private List<Orders> getOrdersList() {
        try (SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Orders.class).buildSessionFactory();
             Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            return session.createQuery("from Orders").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while fetching orders.", e);
        }
    }
    private List<Consumables> getConsumablesList() {
        try (SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Consumables.class).buildSessionFactory();
             Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            return session.createQuery("from Consumables").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while fetching consumables.", e);
        }
    }

    @Override
    public List<Orders> SelectOrders() {
        List<Orders> list = getOrdersList();

        LocalDate today = LocalDate.now();
        return list.stream()
                .filter(obj -> {
                    LocalDate expirationDate = LocalDate.parse(obj.getExpiration_date());
                    return expirationDate.isBefore(today.plusMonths(6)) && !expirationDate.isBefore(today);
                })
                .toList();
    }

    @Override
    public List<Consumables> SelectPersonalOrders() {
        List<Consumables> list = getConsumablesList();

        LocalDate today = LocalDate.now();
        return list.stream()
                .filter(obj -> {
                    LocalDate expirationDate = LocalDate.parse(obj.getExpiration_date());
                    return expirationDate.isBefore(today.plusMonths(6)) && !expirationDate.isBefore(today);
                })
                .toList();
    }

    @Override
    public List<Orders> SelectOrdersExpired() {
        List<Orders> list = getOrdersList();

        LocalDate today = LocalDate.now();
        return list.stream()
                .filter(obj -> {
                    LocalDate expirationDate = LocalDate.parse(obj.getExpiration_date());
                    return expirationDate.isBefore(today);
                })
                .toList();
    }

    @Override
    public List<Consumables> SelectPersonalOrdersExpired() {
        List<Consumables> list = getConsumablesList();

        LocalDate today = LocalDate.now();
        return list.stream()
                .filter(obj -> {
                    LocalDate expirationDate = LocalDate.parse(obj.getExpiration_date());
                    return expirationDate.isBefore(today);
                })
                .toList();
    }

    @Override
    public Boolean SelectOrdersBoolean() {
        List<Orders> list = SelectOrders();
        return list.isEmpty();
    }

    @Override
    public Boolean SelectPersonalOrdersBoolean() {
        List<Consumables> list = SelectPersonalOrders();
        return list.isEmpty();
    }

    @Override
    public Boolean SelectOrdersBooleanExpired() {
        List<Orders> list = SelectOrdersExpired();
        return list.isEmpty();
    }

    @Override
    public Boolean SelectPersonalOrdersBooleanExpired() {
        List<Consumables> list = SelectPersonalOrdersExpired();
        return list.isEmpty();
    }
}
