package com.social.twitter.authentication.internal.service;

import com.social.twitter.authentication.RoleService;
import com.social.twitter.authentication.entity.Role;
import com.social.twitter.authentication.internal.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }


}
