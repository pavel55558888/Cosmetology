package com.example.cosmetology.mail.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mail_tracking")
public class MailTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "date")
    private String date;

    public MailTracking() {
    }

    public MailTracking(String username, String date) {
        this.username = username;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
