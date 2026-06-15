package com.social.twitter.connections;


import com.social.twitter.connections.internal.dto.FollowerFolloweeDTO;
import com.social.twitter.connections.internal.entity.ConnectionEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

public interface ConnectionService {

    ConnectionEntity addConnection(ConnectionEntity connectionEntity);

    String deleteConnectionByEntity(ConnectionEntity connectionEntity);

    String deleteConnectionById(long connectionId);

    List<ConnectionEntity> getFollowersOfAUser(Long userId);

    List<ConnectionEntity> getUsersFollowedByUser(Long userId);

    ConnectionEntity findByFollowerAndFolloweeId(Long followerId, Long followeeId);

    String deleteConnectionByFollowerAndFolloweeId(Long followerId, Long followeeId);

    FollowerFolloweeDTO getUserConnections( Long userId);

    Set<Long> getUserFollowees(Long userId);
    Set<Long> getUserFollowers(Long userId);

    void deleteAllConnectionsByUserId(Long userId);
}