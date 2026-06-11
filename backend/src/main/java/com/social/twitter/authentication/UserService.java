package com.social.twitter.authentication;

import com.social.twitter.authentication.dto.RegistrationRequest;
import com.social.twitter.authentication.entity.UpdatedUser;
import com.social.twitter.authentication.entity.User;

import java.util.List;

public interface UserService {

    User getUserByUserId(Long userId);
    User addUser(RegistrationRequest registrationRequest);
    User updateUser(UpdatedUser updatedUser);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void deleteUser(User user);
    List<Long> getSuggestionsForFollowers(List<Long> followees, Long userId);
    List<Long> getUserSearchResult(String input);
}
