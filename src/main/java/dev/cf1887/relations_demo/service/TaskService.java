package dev.cf1887.relations_demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.cf1887.relations_demo.dao.ProjectRepository;
import dev.cf1887.relations_demo.dao.TagRepository;
import dev.cf1887.relations_demo.dao.TaskRepository;
import dev.cf1887.relations_demo.dto.TagResponse;
import dev.cf1887.relations_demo.dto.TaskCreateRequest;
import dev.cf1887.relations_demo.dto.TaskResponse;
import dev.cf1887.relations_demo.entity.Project;
import dev.cf1887.relations_demo.entity.Tag;
import dev.cf1887.relations_demo.entity.Task;
import dev.cf1887.relations_demo.exception.NotFoundException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private final TagRepository tagRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository,
            TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
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

    @Transactional
    public void addTagToTask(Long projectId, Long taskId, Long tagId) {
        // Find the referring project
        Optional<Project> projectOptional = projectRepository.findById(tagId);
        // If the referring project does not exist
        if (projectOptional.isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
        Project project = projectOptional.get();
        // Find the referring task
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        // If the referring task does not exist
        if (taskOptional.isEmpty()) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        Task task = taskOptional.get();
        // Sanity-check: Tasks projectId should match the given projectId
        if (!task.getProject().getId().equals(project.getId())) {
            throw new NotFoundException("Task " + taskId + " does not belong to project " + projectId);
        }
        // Load tag
        Optional<Tag> tagOptional = tagRepository.findById(tagId);
        if (tagOptional.isEmpty()) {
            throw new NotFoundException("Tag not found: " + tagId);
        }
        Tag tag = tagOptional.get();
        // Save the tag to the task
        task.addTag(tag);
        taskRepository.save(task); // <- this is optional, since JPA notices a change from "addTag()" and does the
                                   // same already in the background. However it is not bad to ensure this anyways.
    }

    @Transactional(readOnly = true)
    public List<TagResponse> listTagsOfTask(Long projectId, Long taskId) {
        // Get the referring task
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        // If the referring task does not exist
        if (taskOptional.isEmpty()) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        Task task = taskOptional.get();
        // Sanity-check: Tasks projectId should match the given projectId
        if (!task.getProject().getId().equals(projectId)) {
            throw new NotFoundException("Task " + taskId + " does not belong to project " + projectId);
        }
        // Create a proper list of dtos as response
        return task.getTags().stream()
                .map(tag -> new TagResponse(tag.getId(), tag.getName()))
                .toList();
    }
}
