package com.example.cosmetology.repository;

import com.example.cosmetology.models.DailyProfit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyProfitRepo extends JpaRepository<DailyProfit, Long> {
}
