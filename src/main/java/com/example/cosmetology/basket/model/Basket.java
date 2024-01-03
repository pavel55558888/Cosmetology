package com.example.cosmetology.basket.model;
import jakarta.persistence.*;

@Entity
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

}
