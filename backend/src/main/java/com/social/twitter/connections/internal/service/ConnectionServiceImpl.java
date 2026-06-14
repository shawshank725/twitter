package com.social.twitter.connections.internal.service;

import com.social.twitter.connections.ConnectionService;
import com.social.twitter.connections.internal.dto.FollowerFolloweeDTO;
import com.social.twitter.connections.internal.entity.ConnectionEntity;
import com.social.twitter.connections.internal.repository.ConnectionRepository;
import com.social.twitter.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final NotificationService notificationService;

    @Override
    public ConnectionEntity addConnection(ConnectionEntity connectionEntity){
        ConnectionEntity savedConnection = connectionRepository.save(connectionEntity);
        notificationService.createFollowNotification(
                connectionEntity.getFolloweeId(), connectionEntity.getFollowerId(),
                null);
        return savedConnection;
    }

    @Override
    public String deleteConnectionByEntity(ConnectionEntity connectionEntity){
        try {
            connectionRepository.delete(connectionEntity);
            return "success";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "failure";
        }
    }

    @Override
    public String deleteConnectionById(long connectionId){
        try {
            connectionRepository.deleteById(connectionId);
            return "success";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "failure";
        }
    }

    @Override
    public List<ConnectionEntity> getFollowersOfAUser(Long userId){
        return connectionRepository.findAllByFolloweeId(userId);
    }

    @Override
    public List<ConnectionEntity> getUsersFollowedByUser(Long userId){
        return connectionRepository.findAllByFollowerId(userId);
    }

    @Override
    public ConnectionEntity findByFollowerAndFolloweeId(Long followerId, Long followeeId){
        return connectionRepository.findByFollowerIdAndFolloweeId(followerId, followeeId).orElse(null);
    }

    @Override
    public String deleteConnectionByFollowerAndFolloweeId(Long followerId, Long followeeId){
        try {
            ConnectionEntity connectionEntity = connectionRepository.findByFollowerIdAndFolloweeId(followerId, followeeId).orElse(null);
            assert connectionEntity != null;
            connectionRepository.delete(connectionEntity);
            return "success";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "failure";
        }
    }

    @Override
    public FollowerFolloweeDTO getUserConnections(Long userId){
        FollowerFolloweeDTO dto = new FollowerFolloweeDTO();

        dto.setFolloweeList(getUsersFollowedByUser(userId));
        dto.setFollowerList(getFollowersOfAUser(userId));

        return dto;
    }

    @Override
    public Set<Long> getUserFollowees(Long userId) {
        Set<Long> followees = getFollowersOfAUser(userId)
                .stream()
                .map(ConnectionEntity::getFolloweeId)
                .collect(Collectors.toSet());
        return followees;
    }

    @Override
    public Set<Long> getUserFollowers(Long userId) {
        Set<Long> followers = getFollowersOfAUser(userId)
                .stream()
                .map(ConnectionEntity::getFollowerId)
                .collect(Collectors.toSet());
        return followers;
    }
}
