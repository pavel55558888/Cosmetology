package com.example.cosmetology;

import com.example.cosmetology.expired_product.service.impl.ExpiredProductImpl;
import com.example.cosmetology.monthly_report.service.impl.MonthlyReportImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CosmetologyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosmetologyApplication.class, args);
    }

}
