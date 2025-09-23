package dev.cf1887.relations_demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cf1887.relations_demo.dto.TagResponse;
import dev.cf1887.relations_demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/tags")
@Tag(name = "Task Tags")
public class TaskTagController {

    private final TaskService taskService;

    public TaskTagController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{tagId}")
    @Operation(summary = "Attach an existing tag to a task")
    public ResponseEntity<Void> addTag(@PathVariable Long projectId,
            @PathVariable Long taskId,
            @PathVariable Long tagId) {
        taskService.addTagToTask(projectId, taskId, tagId);
        // Return 204 with location of the list
        URI location = URI.create("/api/projects/" + projectId + "/tasks/" + taskId + "/tags");
        return ResponseEntity.noContent().location(location).build();
    }

    @GetMapping
    @Operation(summary = "List tags of a task")
    public List<TagResponse> list(@PathVariable Long projectId, @PathVariable Long taskId) {
        return taskService.listTagsOfTask(projectId, taskId);
    }
}
