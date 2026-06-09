package com.social.twitter.authentication.entity;


import java.time.LocalDate;

public record UserDTO(
        Long userId,
        String username,
        String name,
        String email,
        String bio,
        String website,
        String location,
        String profilePhoto,
        String backgroundPhoto,
        LocalDate joinedDate
) {
}
