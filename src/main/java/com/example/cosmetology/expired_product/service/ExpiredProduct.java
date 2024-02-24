package com.example.cosmetology.expired_product.service;

import com.example.cosmetology.orderspersonal.model.Consumables;
import com.example.cosmetology.orders.model.Orders;

import java.util.List;

public interface ExpiredProduct {
    List<Orders> SelectOrders();
    List<Consumables> SelectPersonalOrders();
    List<Orders> SelectOrdersExpired();
    List<Consumables> SelectPersonalOrdersExpired();

    Boolean SelectOrdersBoolean();
    Boolean SelectPersonalOrdersBoolean();
    Boolean SelectOrdersBooleanExpired();
    Boolean SelectPersonalOrdersBooleanExpired();
}
