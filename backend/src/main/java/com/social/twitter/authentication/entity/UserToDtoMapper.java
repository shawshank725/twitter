package com.social.twitter.authentication.entity;

public class UserToDtoMapper {

    public static UserDTO userToDTOMapper(User user){
        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getBio(),
                user.getWebsite(),
                user.getLocation(),
                user.getProfilePhoto(),
                user.getBackgroundPhoto(),
                user.getJoinedDate()
        );
    }
}
