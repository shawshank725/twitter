package com.social.twitter.authentication.internal.service;

import com.social.twitter.authentication.UserService;
import com.social.twitter.authentication.internal.dto.RegistrationRequest;
import com.social.twitter.authentication.internal.entity.Role;
import com.social.twitter.authentication.internal.entity.UpdatedUser;
import com.social.twitter.authentication.internal.entity.User;
import com.social.twitter.authentication.internal.repository.RoleRepository;
import com.social.twitter.authentication.internal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${twitter.profile-photo}")
    private String DEFAULT_PROFILE_PHOTO;

    @Value("${twitter.background-photo}")
    private String DEFAULT_BACKGROUND_PHOTO;

    @Override
    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User addUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setEmail(registrationRequest.email());
        user.setUsername(registrationRequest.username());
        user.setName(registrationRequest.name());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));
        user.setProfilePhoto(DEFAULT_PROFILE_PHOTO);
        user.setBackgroundPhoto(DEFAULT_BACKGROUND_PHOTO);
        user.setEnabled(true);

        // creating date and creating local date as well
        Date input = new Date();
        LocalDate date = LocalDate.ofInstant(input.toInstant(), ZoneId.systemDefault());
        user.setJoinedDate(date);

        Role userRole = roleRepository.findByRoleName("ROLE_USER");
        user.setRole(userRole);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdatedUser updatedUser) {
        Optional<User> optionalUser = userRepository.findById(updatedUser.userId());
        User userFromDatabase = optionalUser.orElse(null);

        if (userFromDatabase != null){
            userFromDatabase.setName(updatedUser.name());
            userFromDatabase.setBackgroundPhoto(updatedUser.backgroundPhoto());
            userFromDatabase.setProfilePhoto(updatedUser.profilePhoto());
            userFromDatabase.setBio(updatedUser.bio());
            userFromDatabase.setWebsite(updatedUser.website());
            userFromDatabase.setLocation(updatedUser.location());
            return userRepository.save(userFromDatabase);
        }

        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        // when deleting a user account, delete their posts, delete the post media that they uploaded
        // and then delete the user itself.

        userRepository.delete(user);
    }

    @Override
    public List<Long> getSuggestionsForFollowers(List<Long> followees, Long userId) {
        if (followees == null) {
            followees = List.of(); // empty list
        }
        Set<Long> followeeSet = new HashSet<>(followees);

        List<Long> candidates = new ArrayList<>(userRepository.findAll().stream()
                .map(User::getUserId)
                .filter(id -> !id.equals(userId))
                .filter(id -> !followeeSet.contains(id))
                .toList());

        Collections.shuffle(candidates);
        return candidates.stream().limit(4).toList();
    }

    @Override
    public List<Long> getUserSearchResult(String input) {
        return userRepository.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCase(input, input)
                .stream()
                .map(User::getUserId)
                .toList();
    }
}
