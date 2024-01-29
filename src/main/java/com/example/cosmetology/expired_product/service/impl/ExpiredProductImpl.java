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

    @Override
    public List<Orders> SelectOrders() {
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

        List<Orders> listFilter = list.stream()
                .filter(obj -> {
                    LocalDate date = LocalDate.parse(obj.getExpiration_date());
                    LocalDate today = LocalDate.now();
                    return date.isBefore(today.plusMonths(5)) || date.isEqual(today.plusMonths(5));
                })
                .toList();
        return listFilter;
    }

    @Override
    public List<Consumables> SelectPersonalOrders() {
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

        List<Consumables> listFilter = list.stream()
                .filter(obj -> {
                    LocalDate date = LocalDate.parse(obj.getExpiration_date());
                    LocalDate today = LocalDate.now();
                    return date.isBefore(today.plusMonths(5)) || date.isEqual(today.plusMonths(5));
        })
                .toList();
        return listFilter;
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
}
