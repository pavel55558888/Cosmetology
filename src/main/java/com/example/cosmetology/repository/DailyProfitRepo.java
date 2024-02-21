package com.example.cosmetology.repository;

import com.example.cosmetology.daily_profit.model.DailyProfit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyProfitRepo extends JpaRepository<DailyProfit, Long> {
}
