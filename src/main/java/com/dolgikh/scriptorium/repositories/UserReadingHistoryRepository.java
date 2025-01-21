package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.UserReadingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReadingHistoryRepository extends JpaRepository<UserReadingHistory, Integer> {
}
