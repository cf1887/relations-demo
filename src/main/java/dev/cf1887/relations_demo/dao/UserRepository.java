package dev.cf1887.relations_demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cf1887.relations_demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
