package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.UserReadingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReadingHistoryRepository extends JpaRepository<UserReadingHistory, Integer> {
    Optional<UserReadingHistory> findByUserIdAndBookId(int userId, int bookId);

    void deleteByUserIdAndBookId(int userId, int bookId);
}
