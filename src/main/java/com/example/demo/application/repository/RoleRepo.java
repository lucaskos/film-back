package com.example.demo.application.repository;

import com.example.demo.application.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findRoleByRole(String role);

}
