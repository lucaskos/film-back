package com.luke.filmdb.application.repositories;

import com.luke.filmdb.application.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findRoleByRoleName(String roleName);

}
