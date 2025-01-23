package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.models.UserReadingHistory;
import com.dolgikh.scriptorium.repositories.BooksRepository;
import com.dolgikh.scriptorium.repositories.UserAccountRepository;
import com.dolgikh.scriptorium.repositories.UserReadingHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final UserReadingHistoryRepository userReadingHistoryRepository;
    private final BooksRepository booksRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, UserReadingHistoryRepository userReadingHistoryRepository, BooksRepository booksRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.userReadingHistoryRepository = userReadingHistoryRepository;
        this.booksRepository = booksRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @Transactional
    public void save(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccountRepository.save(userAccount);
    }

    @Transactional
    public void addBookToRead(UserAccount userAccount, int bookId) {
        Book book = booksRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Книга с id " + bookId + " не найдена"));

        if (userAccountRepository.findById(userAccount.getId()).isEmpty())
            throw new IllegalArgumentException("Пользователь с id " + userAccount.getId() + " не найден");

        if (userReadingHistoryRepository.findByUserIdAndBookId(userAccount.getId(), bookId).isPresent())
            throw new EntityNotFoundException("Книга с id " + bookId + " уже прочитана");

        userReadingHistoryRepository.save(new UserReadingHistory(userAccount, book, new Date()));
    }

    @Transactional
    public void deleteBookFromRead(int userId, int bookId) {
        if (booksRepository.findById(bookId).isEmpty())
            throw new IllegalArgumentException("Книга с id " + bookId + " не найдена");

        if (userAccountRepository.findById(userId).isEmpty())
            throw new IllegalArgumentException("Пользователь с id " + userId + " не найден");

        if (userReadingHistoryRepository.findByUserIdAndBookId(userId, bookId).isEmpty())
            throw new EntityNotFoundException("Книга с id " + bookId + " ещё не прочитана");

        userReadingHistoryRepository.deleteByUserIdAndBookId(userId, bookId);
    }
}
