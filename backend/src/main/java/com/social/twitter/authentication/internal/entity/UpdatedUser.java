package com.social.twitter.authentication.internal.entity;


public record UpdatedUser (
        Long userId,
        String name,
        String bio,
        String website,
        String profilePhoto,
        String backgroundPhoto,
        String location
){
}
