package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepositoty extends JpaRepository<UserAccount, Integer> {
    UserAccount findByUsername(String username);
}
