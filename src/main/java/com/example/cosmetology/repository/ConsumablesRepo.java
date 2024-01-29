package com.example.cosmetology.repository;

import com.example.cosmetology.orderspersonal.model.Consumables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumablesRepo extends JpaRepository<Consumables,Long> {
}
