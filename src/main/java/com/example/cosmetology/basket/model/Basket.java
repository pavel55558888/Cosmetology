package com.example.cosmetology.basket.model;
import com.example.cosmetology.basket.service.impl.BasketServiceImpl;
import jakarta.persistence.*;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "DeleteFirstTwoItems",
                query = "CALL DeleteFirstTwoItems()",
                resultClass = BasketServiceImpl.class)
})
@Entity
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", length = 255)
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private String price;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "country_of_manufacture")
    private String countryOfManufacture;
    @Column(name = "img")
    private String img;
    @Column(name = "purchase_price")
    private String purchasePrice;

    public Basket(String username, String name, String price, String manufacturer, String countryOfManufacture, String img, String purchasePrice) {
        this.username = username;
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.countryOfManufacture = countryOfManufacture;
        this.img = img;
        this.purchasePrice = purchasePrice;
    }

    public Basket() {
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCountryOfManufacture() {
        return countryOfManufacture;
    }

    public void setCountryOfManufacture(String countryOfManufacture) {
        this.countryOfManufacture = countryOfManufacture;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
