package com.example.cosmetology.basket.service;

import com.example.cosmetology.basket.model.Basket;

import java.util.List;

public interface BasketService {
    void basketAdd(String name,String price,String manufacturer,String countryOfManufacture,String img,String purchasePrice);
    void basketDelete(long id);
    List<Basket> basketSelect();
    void basketDeleteName(String name);
    void storedProcedures();
    void clearBasket();
}
