package dev.cf1887.relations_demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.cf1887.relations_demo.dao.CommentRepository;
import dev.cf1887.relations_demo.dao.ProjectRepository;
import dev.cf1887.relations_demo.dao.TaskRepository;
import dev.cf1887.relations_demo.dto.CommentCreateRequest;
import dev.cf1887.relations_demo.dto.CommentResponse;
import dev.cf1887.relations_demo.entity.Comment;
import dev.cf1887.relations_demo.entity.Project;
import dev.cf1887.relations_demo.entity.Task;
import dev.cf1887.relations_demo.exception.NotFoundException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository,
            ProjectRepository projectRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public CommentResponse add(Long projectId, Long taskId, CommentCreateRequest dto) {
        // Search for referring project
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
        Project project = projectOptional.get();
        // Search for referring task
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        Task task = taskOptional.get();
        // Sanity check
        if (!task.getProject().getId().equals(project.getId())) {
            throw new NotFoundException("Task " + taskId + " does not belong to project " + projectId);
        }
        // Create new Comment instance
        Comment tempComment = new Comment();
        tempComment.setContent(dto.getContent().trim());
        tempComment.setTask(task);
        // Save Comment instance to database
        Comment saved = commentRepository.save(tempComment);
        return new CommentResponse(saved.getId(), saved.getContent(), saved.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> list(Long projectId, Long taskId) {
        // Search for referring task
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        Task task = taskOptional.get();
        // Sanity check
        if (!task.getProject().getId().equals(projectId)) {
            throw new NotFoundException("Task " + taskId + " does not belong to project " + projectId);
        }
        // Convert entities into dtos
        return commentRepository.findByTaskIdOrderByCreatedAtAsc(taskId)
                .stream()
                .map(comment -> new CommentResponse(comment.getId(), comment.getContent(), comment.getCreatedAt()))
                .toList();
    }
}
