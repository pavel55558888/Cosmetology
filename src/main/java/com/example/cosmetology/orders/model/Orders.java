package com.example.cosmetology.orders.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private String price;
    @Column(name = "expiration_date")
    private String expiration_date;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "country_of_manufacture")
    private String country_of_manufacture;
    @Column(name = "purpose_of_use")
    private String purpose_of_use;
    @Column(name = "img")
    private String img;
    @Column(name = "purchase_price")
    private String purchase_price;
    @Column(name = "quantity")
    private String quantity;
    @Column(name = "description", length = 18000)
    private String description;
    @Column(name = "current_datee")
    private String currenDate;
    @Column(name = "code_orders")
    private String code;

    public Orders() {
    }

    public Orders(String name, String price, String expiration_date, String manufacturer, String country_of_manufacture, String purpose_of_use, String img, String purchase_price, String quantity, String description, String currenDate, String code) {
        this.name = name;
        this.price = price;
        this.expiration_date = expiration_date;
        this.manufacturer = manufacturer;
        this.country_of_manufacture = country_of_manufacture;
        this.purpose_of_use = purpose_of_use;
        this.img = img;
        this.purchase_price = purchase_price;
        this.quantity = quantity;
        this.description = description;
        this.currenDate = currenDate;
        this.code = code;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getCountry_of_manufacture() {
        return country_of_manufacture;
    }

    public void setCountry_of_manufacture(String country_of_manufacture) {
        this.country_of_manufacture = country_of_manufacture;
    }

    public String getPurpose_of_use() {
        return purpose_of_use;
    }

    public void setPurpose_of_use(String purpose_of_use) {
        this.purpose_of_use = purpose_of_use;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrenDate() {
        return currenDate;
    }

    public void setCurrenDate(String currenDate) {
        this.currenDate = currenDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orders orders)) return false;
        return Objects.equals(getName(), orders.getName()) && Objects.equals(getImg(), orders.getImg()) && Objects.equals(getCode(), orders.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getImg(), getCode());
    }
}
