package com.social.twitter.authentication;


import com.social.twitter.authentication.internal.entity.Role;

public interface RoleService  {

    Role findByRoleName(String roleName);
}
