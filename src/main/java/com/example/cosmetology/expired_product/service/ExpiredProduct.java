package com.example.cosmetology.expired_product.service;

import com.example.cosmetology.orderspersonal.model.Consumables;
import com.example.cosmetology.orders.model.Orders;

import java.util.List;

public interface ExpiredProduct {
    List<Orders> SelectOrders();
    List<Consumables> SelectPersonalOrders();

    Boolean SelectOrdersBoolean();
    Boolean SelectPersonalOrdersBoolean();
}
