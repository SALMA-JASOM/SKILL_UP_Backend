package com.example.SpringBoot_SKILLUP.repository;

import  com.example.SpringBoot_SKILLUP.model.User;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Ajoute cette méthode pour rechercher un utilisateur par email
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);  // Add this method
}