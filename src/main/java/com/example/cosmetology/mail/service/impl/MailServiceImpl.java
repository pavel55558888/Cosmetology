package com.example.cosmetology.mail.service.impl;

import com.example.cosmetology.mail.model.MailTracking;
import com.example.cosmetology.mail.service.MailService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MailServiceImpl implements MailService {

    @Override
    public boolean tracking() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        SessionFactory factory = new Configuration().configure("cosmetology.cfg.xml").addAnnotatedClass(MailTracking.class).buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            List<MailTracking> list = session.createQuery("from MailTracking where username = :param1 and date = :param2").setParameter("param1",username).setParameter("param2", formattedDate).getResultList();

            if (list.isEmpty() || list.size() == 1) {
                MailTracking hibernate = new MailTracking(username, formattedDate);
                session.persist(hibernate);
                session.getTransaction().commit();
            } else if (list.size() >= 2) {
                session.getTransaction().commit();
                return false;
            }
        } finally {
            factory.close();
        }
    return true;
    }


}
