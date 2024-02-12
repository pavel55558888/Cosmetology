package com.example.cosmetology.Index.Model;

import jakarta.persistence.*;

@Entity
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "img")
    private String img;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public Services(String img, String name, String description) {
        this.img = img;
        this.name = name;
        this.description = description;
    }

    public Services() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
