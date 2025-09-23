package dev.cf1887.relations_demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cf1887.relations_demo.dto.TaskCreateRequest;
import dev.cf1887.relations_demo.dto.TaskResponse;
import dev.cf1887.relations_demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@Tag(name = "Tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create a task for a project")
    public ResponseEntity<TaskResponse> create(@PathVariable Long projectId,
            @Valid @RequestBody TaskCreateRequest dto) {
        // Assign taskService to this create operation
        TaskResponse saved = taskService.create(projectId, dto);
        // Create a proper success message
        URI location = URI.create("/api/projects/" + projectId + "/tasks/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    @Operation(summary = "List tasks of a project")
    public List<TaskResponse> list(@PathVariable Long projectId) {
        return taskService.listByProject(projectId);
    }

}
