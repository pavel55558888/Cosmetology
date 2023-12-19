package com.example.cosmetology.models;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Table(name = "consumables")
public class Consumables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "purchase_price")
    private String purchase_price;
    @Column(name = "expiration_date")
    private String expiration_date;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "img")
    private String img;
    @Column(name = "in_stock")
    private String in_stock;

    public Consumables(String name, String purchase_price, String expiration_date, String manufacturer, String img, String in_stock) {
        this.name = name;
        this.purchase_price = purchase_price;
        this.expiration_date = expiration_date;
        this.manufacturer = manufacturer;
        this.img = img;
        this.in_stock = in_stock;
    }

    public Consumables() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(String in_stock) {
        this.in_stock = in_stock;
    }

    @Override
    public String toString() {
        return "Consumables{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", purchase_price='" + purchase_price + '\'' +
                ", expiration_date='" + expiration_date + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", img='" + img + '\'' +
                ", in_stock='" + in_stock + '\'' +
                '}';
    }
}
