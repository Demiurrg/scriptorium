package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.models.UserReadingHistory;
import com.dolgikh.scriptorium.repositories.UserAccountRepository;
import com.dolgikh.scriptorium.repositories.UserReadingHistoryRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final UserReadingHistoryRepository userReadingHistoryRepository;
    private final BooksService booksService;
    private final PasswordEncoder passwordEncoder;

    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with name " + username + " was not found"));
    }

    public void checkUserExistence(long id) {
        if(!userAccountRepository.existsById(id))
            throw new UserNotFoundException(id);
    }

    public boolean doesUserExist(String username) {
        return userAccountRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void save(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccountRepository.save(userAccount);
    }

    @Transactional
    public void addBookToRead(UserAccount userAccount, long bookId) {
        Book book = booksService.findOne(bookId);
        checkUserExistence(userAccount.getId());

        if (userReadingHistoryRepository.findByUserIdAndBookId(userAccount.getId(), bookId).isPresent())
            throw new IllegalArgumentException("Book with id " + bookId + " has already been read");

        userReadingHistoryRepository.save(new UserReadingHistory(userAccount, book));
    }

    @Transactional
    public void deleteBookFromRead(long userId, long bookId) {
        booksService.checkBookExistence(bookId);
        checkUserExistence(userId);

        if (userReadingHistoryRepository.findByUserIdAndBookId(userId, bookId).isEmpty())
            throw new IllegalArgumentException("Book with id " + bookId + " has not been read yet");

        userReadingHistoryRepository.deleteByUserIdAndBookId(userId, bookId);
    }
}
