package com.social.twitter.connections.internal.dto;

import com.social.twitter.connections.internal.entity.ConnectionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerFolloweeDTO {

    private List<ConnectionEntity> followeeList;
    private List<ConnectionEntity> followerList;

}