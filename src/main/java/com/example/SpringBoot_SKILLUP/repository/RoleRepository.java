package com.example.SpringBoot_SKILLUP.repository;
import com.example.SpringBoot_SKILLUP.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Short> {
    Optional<Role> findByName(String name);  // Add this method if missing
}
