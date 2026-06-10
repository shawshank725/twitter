package com.social.twitter.authentication.internal.controller;

import com.social.twitter.authentication.entity.User;
import com.social.twitter.authentication.entity.UserDTO;
import com.social.twitter.authentication.entity.UserToDtoMapper;
import com.social.twitter.authentication.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

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
    public User updateUserProfile(@RequestBody User User) {
        User savedUser = userService.saveUser(User);
        return savedUser;
    }

    @GetMapping("/getUserByUserId")
    public User getUserByUserId(@RequestParam Long userId){
        return userService.getUserByUserId(userId);
    }


}
