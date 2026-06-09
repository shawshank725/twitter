package com.social.twitter.authentication.dto;

public record RegistrationRequest(
        String username,
        String email,
        String password,
        String name
) {}