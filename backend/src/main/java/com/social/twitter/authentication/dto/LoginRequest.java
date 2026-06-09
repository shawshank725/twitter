package com.social.twitter.authentication.dto;

public record LoginRequest(
        String username,
        String password
) {
}
