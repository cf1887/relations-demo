package dev.cf1887.relations_demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cf1887.relations_demo.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
