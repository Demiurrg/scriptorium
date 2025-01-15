package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.repositories.UserAccountRepositoty;
import com.dolgikh.scriptorium.security.UserAccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAccountDetailsService implements UserDetailsService {
    private final UserAccountRepositoty userAccountRepositoty;

    @Autowired
    public UserAccountDetailsService(UserAccountRepositoty userAccountRepositoty) {
        this.userAccountRepositoty = userAccountRepositoty;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepositoty.findByUsername(username);

        if (userAccount == null)
            throw new UsernameNotFoundException("User was not found");

        return new UserAccountDetails(userAccount);
    }
}
