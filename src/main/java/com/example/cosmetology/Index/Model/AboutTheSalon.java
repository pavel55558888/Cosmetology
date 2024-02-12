package com.example.cosmetology.Index.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "about_the_salon")
public class AboutTheSalon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "activity",length = 5000)
    private String activity;
    @Column(name = "history", length = 5000)
    private String History;

    public AboutTheSalon() {
    }

    public AboutTheSalon(String activity, String history) {
        this.activity = activity;
        History = history;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getHistory() {
        return History;
    }

    public void setHistory(String history) {
        History = history;
    }
}
