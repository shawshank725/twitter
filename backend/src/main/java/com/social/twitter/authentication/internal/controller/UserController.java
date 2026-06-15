package com.social.twitter.authentication.internal.controller;

import com.social.twitter.authentication.internal.entity.UpdatedUser;
import com.social.twitter.authentication.internal.entity.User;
import com.social.twitter.authentication.internal.entity.UserDTO;
import com.social.twitter.authentication.internal.entity.UserToDtoMapper;
import com.social.twitter.authentication.UserService;
import com.social.twitter.orchestration.OrchestrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OrchestrationService orchestrationService;

    // get the current logged-in user information from this endpoint
    @GetMapping("/me")
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        return UserToDtoMapper.userToDTOMapper(user);
    }

    @GetMapping("{username}")
    public UserDTO getUserInformation(@PathVariable String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userService.getUserByUsername(username);
        return UserToDtoMapper.userToDTOMapper(user);
    }

    @GetMapping("usernameAvailable")
    public boolean usernameAvailable(@RequestParam String username){
        User user = userService.getUserByUsername(username);
        return user == null;
    }

    @GetMapping("emailAvailable")
    public boolean emailAvailable(@RequestParam String email){
        User user = userService.getUserByEmail(email);
        return user == null;
    }

    @GetMapping("/getUserIdByUsername")
    public long getUserIdByUsername (@RequestParam String username){
        return userService.getUserByUsername(username).getUserId();
    }

    @PostMapping("/updateUserProfile")
    public User updateUserProfile(@RequestBody UpdatedUser updatedUser) {
        return userService.updateUser(updatedUser);
    }

    @GetMapping("/getUserByUserId")
    public User getUserByUserId(@RequestParam Long userId){
        return userService.getUserByUserId(userId);
    }


    @PostMapping("/updateUsername")
    public String updateUsername(@RequestParam String oldUsername,
                                 @RequestParam String newUsername,
                                 @RequestParam String password) {
        User userEntity = userService.getUserByUsername(oldUsername);
        if (userEntity == null) {
            return "failure: user not found";
        }

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            return "failure: passwords don't match";
        }

        userEntity.setUsername(newUsername);
        userService.updateUser(userEntity);
        return "success";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String username,@RequestParam String oldPassword, @RequestParam String newPassword){
        User userEntity = userService.getUserByUsername(username);
        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            return "failure: passwords don't match";
        }
        else {
            userEntity.setPassword(passwordEncoder.encode(newPassword));
            userService.updateUser(userEntity);
            return "success";
        }
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(@RequestParam String username, @RequestParam String password) {
        User userEntity = userService.getUserByUsername(username);
        if (userEntity != null) {
            if (!passwordEncoder.matches(password, userEntity.getPassword())) {
                return "failure: incorrect password";
            }
            try {
                // do not delete the posts, replies, quote posts of a user when they delete their account
                // instead delete the likes and bookmarks done by that user.
                // then delete the user account.
                // however the following service layer deletes the likes and connection entities.
                // this is done because using the like and bookmark services directly causes the
                // modularity test case to fail and give cyclic dependency error
                orchestrationService.deleteUserAccountAndLikesAndConnections(userEntity.getUserId());
                userService.deleteUser(userEntity);
                return "success";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "failure: could not delete";
            }
        } else {
            return "failure: user not found";
        }
    }

}
