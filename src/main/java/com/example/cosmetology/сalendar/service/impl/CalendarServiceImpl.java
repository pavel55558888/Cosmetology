package com.example.cosmetology.сalendar.service.impl;

import com.example.cosmetology.сalendar.model.Calendar;
import com.example.cosmetology.сalendar.service.CalendarService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class CalendarServiceImpl implements CalendarService {
    @Override
    public void calendarAdd(String firstname, String name, String lastname, String number, String date, String time) {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Calendar.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            Calendar hibernate = new Calendar(firstname, name, lastname,number,date,time);
            session.beginTransaction();
            session.persist(hibernate);
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    @Override
    public List<Calendar> caledarSelectDate(String date) {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Calendar.class).buildSessionFactory();
        List<Calendar> list;
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            list = session.createQuery("from Calendar where date = :param1").setParameter("param1", date).list();
            session.getTransaction().commit();
        } finally {
            factory.close();
        }

        return list;
    }

    @Override
    public List<Calendar> caledarSelectName(String firstname, String name) {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Calendar.class).buildSessionFactory();
        List<Calendar> list;
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            list = session.createQuery("from Calendar where firstname = :param1 and name = :param2").setParameter("param1", firstname).setParameter("param2", name).list();
            session.getTransaction().commit();
        } finally {
            factory.close();
        }

        return list;
    }

    @Override
    public void calendarDelete(long id) {
        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(Calendar.class).buildSessionFactory();
        List<Calendar> list;
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete Calendar where id = :param1").setParameter("param1", id).executeUpdate();
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }
}
