package com.social.twitter.authentication.internal.dto;

public record LoginRequest(
        String username,
        String password
) {
}
