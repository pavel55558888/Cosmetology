package com.example.cosmetology.repository;

import com.example.cosmetology.Index.Model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlesRepo extends JpaRepository<Articles, Long> {
}
