package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.UserReadingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReadingHistoryRepository extends JpaRepository<UserReadingHistory, Long> {
    Optional<UserReadingHistory> findByUserIdAndBookId(long userId, long bookId);

    void deleteByUserIdAndBookId(long userId, long bookId);
}
