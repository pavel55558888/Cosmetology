package com.example.cosmetology.Index.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "address_of_the_salon")
public class AddressOfTheSalon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "address")
    private String address;

    @Column(name = "img")
    private String img;

    public AddressOfTheSalon() {
    }

    public AddressOfTheSalon(String address, String img) {
        this.address = address;
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
