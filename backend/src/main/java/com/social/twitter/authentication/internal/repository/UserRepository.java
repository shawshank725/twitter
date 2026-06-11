package com.social.twitter.authentication.internal.repository;

import com.social.twitter.authentication.internal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);


    List<User> findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCase(String username, String name);
}
