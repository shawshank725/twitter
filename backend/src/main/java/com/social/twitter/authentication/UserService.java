package com.social.twitter.authentication;

import com.social.twitter.authentication.entity.User;

import java.util.List;

public interface UserService {

    User getUserByUserId(Long userId);
    User saveUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void deleteUser(User user);
    List<Long> getSuggestionsForFollowers(List<Long> followees, Long userId);
    List<Long> getUserSearchResult(String input);
}
