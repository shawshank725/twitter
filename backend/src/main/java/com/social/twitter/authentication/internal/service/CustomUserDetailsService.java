package com.social.twitter.authentication.internal.service;

import com.social.twitter.authentication.entity.User;
import com.social.twitter.authentication.entity.UserPrincipal;
import com.social.twitter.authentication.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User doesn't exist");
        }
        else {
            return new UserPrincipal(optionalUser.get());
        }
    }
}
