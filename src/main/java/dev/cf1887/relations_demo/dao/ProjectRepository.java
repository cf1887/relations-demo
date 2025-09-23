package dev.cf1887.relations_demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cf1887.relations_demo.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByName(String name);
}
