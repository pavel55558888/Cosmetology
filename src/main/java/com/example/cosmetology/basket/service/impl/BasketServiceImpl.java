package com.example.cosmetology.basket.service.impl;

import com.example.cosmetology.basket.model.Basket;
import com.example.cosmetology.basket.service.BasketService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.List;

public class BasketServiceImpl implements BasketService {


    @Override
    public void basketAdd(String name, String price, String manufacturer, String countryOfManufacture, String img, String purchasePrice) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        SessionFactory factory = new Configuration().configure("basket.cfg.xml").addAnnotatedClass(Basket.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            Basket hibernate = new Basket(username,name,price,manufacturer,countryOfManufacture,img,purchasePrice);
            session.beginTransaction();
            session.persist(hibernate);
            session.getTransaction().commit();
        } finally {
            factory.close();
        }

    }

    @Override
    public void basketDelete() {

    }

    @Override
    public List<Basket> basketSelect() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        SessionFactory factory = new Configuration().configure("basket.cfg.xml").addAnnotatedClass(Basket.class).buildSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Basket> list = session.createQuery("from Basket where username = :param1").setParameter("param1",username).getResultList();
        System.out.println(username);
        session.getTransaction().commit();
        return list;
    }
}
