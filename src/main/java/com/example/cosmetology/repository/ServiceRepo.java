package com.example.cosmetology.repository;

import com.example.cosmetology.Index.Model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Services, Long> {
}
