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
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Basket.class).buildSessionFactory();
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
    public void basketDelete(long id) {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Basket.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            Basket basket = session.get(Basket.class, id);
            session.delete(basket);

            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    @Override
    public List<Basket> basketSelect() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Basket> list;

        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Basket.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            list = session.createQuery("from Basket where username = :param1").setParameter("param1",username).getResultList();
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
        return list;
    }

    @Override
    public void basketDeleteName(String name) {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Basket.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            session.createQuery("delete Basket where name = :param1").setParameter("param1", name).executeUpdate();

            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    @Override
    public void storedProcedures() {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Basket.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            session.getNamedQuery("DeleteFirstTwoItems").executeUpdate();

            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    @Override
    public void clearBasket() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Basket.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            session.createQuery("delete Basket where username = :param1").setParameter("param1", username).executeUpdate();

            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

}
