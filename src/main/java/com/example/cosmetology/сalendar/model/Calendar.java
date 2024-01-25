package com.example.cosmetology.сalendar.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "calendar")
public class Calendar implements Comparable<Calendar>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "number")
    private String number;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;

    public Calendar() {
    }

    public Calendar(String firstname, String name, String lastname, String number, String date, String time) {
        this.firstname = firstname;
        this.name = name;
        this.lastname = lastname;
        this.number = number;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(Calendar o) {
        return this.getTime().compareTo(o.getTime());
    }
}
