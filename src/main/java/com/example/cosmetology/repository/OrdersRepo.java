package com.example.cosmetology.repository;

import com.example.cosmetology.orders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders, Long> {
}
