package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.repositories.UserAccountRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {
    private final UserAccountRepositoty userAccountRepositoty;

    @Autowired
    public UserAccountService(UserAccountRepositoty userAccountRepositoty) {
        this.userAccountRepositoty = userAccountRepositoty;
    }

    public void save(UserAccount userAccount) {
        userAccountRepositoty.save(userAccount);
    }
}
