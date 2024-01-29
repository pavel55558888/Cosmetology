package com.example.cosmetology.repository;

import com.example.cosmetology.authorization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByMail(String mail);
}
