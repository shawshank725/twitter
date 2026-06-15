package com.social.twitter.orchestration;

public interface OrchestrationService {

    // this is a method that will delete all like entities,
    // all connection entities and the user account itself
    void deleteUserAccountAndLikesAndConnections(Long userId);

}
