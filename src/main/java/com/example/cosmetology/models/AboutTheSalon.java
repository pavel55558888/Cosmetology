package com.example.cosmetology.models;

import jakarta.persistence.*;

@Entity
@Table(name = "about_the_salon")
public class AboutTheSalon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "activity")
    private String activity;
    @Column(name = "history")
    private String History;
    @Column(name = "procedures")
    private String procedures;

    public AboutTheSalon() {
    }

    public AboutTheSalon(String activity, String history, String procedures) {
        this.activity = activity;
        History = history;
        this.procedures = procedures;
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

    public String getProcedures() {
        return procedures;
    }

    public void setProcedures(String procedures) {
        this.procedures = procedures;
    }

    @Override
    public String toString() {
        return "AboutTheSalon{" +
                "activity='" + activity + '\'' +
                ", History='" + History + '\'' +
                ", procedures='" + procedures + '\'' +
                '}';
    }
}
