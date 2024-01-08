package com.example.cosmetology.expired_product.service;

import com.example.cosmetology.models.Consumables;
import com.example.cosmetology.models.Orders;

import java.util.List;

public interface ExpiredProduct {
    List<Orders> SelectOrders();
    List<Consumables> SelectPersonalOrders();

    Boolean SelectOrdersBoolean();
    Boolean SelectPersonalOrdersBoolean();
}
