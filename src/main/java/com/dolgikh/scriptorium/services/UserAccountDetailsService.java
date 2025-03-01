package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.repositories.UserAccountRepository;
import com.dolgikh.scriptorium.security.UserAccountDetails;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountDetailsService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return new UserAccountDetails(userAccount);
    }
}
