package dev.cf1887.relations_demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.cf1887.relations_demo.dao.ProjectRepository;
import dev.cf1887.relations_demo.dao.TaskRepository;
import dev.cf1887.relations_demo.dto.TaskCreateRequest;
import dev.cf1887.relations_demo.dto.TaskResponse;
import dev.cf1887.relations_demo.entity.Project;
import dev.cf1887.relations_demo.entity.Task;
import dev.cf1887.relations_demo.exception.NotFoundException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public TaskResponse create(Long projectId, TaskCreateRequest dto) {
        // Search for referring project
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
        // Create new Task instance
        Task tempTask = new Task();
        tempTask.setTitle(dto.getTitle());
        tempTask.setProject(project.get());

        // Save and get the task with auto-generated values from the database
        Task saved = taskRepository.save(tempTask);
        return new TaskResponse(saved.getId(), saved.getTitle(), saved.getProject().getId());
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> listByProject(Long projectId) {
        // Check if project exists
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundException("Project not found: " + projectId);
        }
        // List all Tasks for this project
        return taskRepository.findByProjectId(projectId).stream()
                .map(task -> new TaskResponse(task.getId(), task.getTitle(), task.getProject().getId()))
                .toList();
    }
}
