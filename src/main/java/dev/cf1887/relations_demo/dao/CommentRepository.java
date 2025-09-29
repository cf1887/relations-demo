package dev.cf1887.relations_demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cf1887.relations_demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskIdOrderByCreatedAtAsc(Long taskId);
}
