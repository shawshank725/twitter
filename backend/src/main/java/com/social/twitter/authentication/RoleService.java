package com.social.twitter.authentication;


import com.social.twitter.authentication.entity.Role;

public interface RoleService  {

    Role findByRoleName(String roleName);
}
