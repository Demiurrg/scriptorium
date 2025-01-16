package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.repositories.UserAccountRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {
    private final UserAccountRepositoty userAccountRepositoty;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepositoty userAccountRepositoty, PasswordEncoder passwordEncoder) {
        this.userAccountRepositoty = userAccountRepositoty;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccountRepositoty.save(userAccount);
    }
}
