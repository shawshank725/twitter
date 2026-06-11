package com.social.twitter.authentication.internal.dto;

public record RegistrationRequest(
        String username,
        String email,
        String password,
        String name
) {}