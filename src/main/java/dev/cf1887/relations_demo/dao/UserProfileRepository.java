package dev.cf1887.relations_demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cf1887.relations_demo.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUser_Id(Long userId);
}
