package dev.cf1887.relations_demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cf1887.relations_demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long theProjectId);
}
