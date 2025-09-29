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

import dev.cf1887.relations_demo.dto.CommentCreateRequest;
import dev.cf1887.relations_demo.dto.CommentResponse;
import dev.cf1887.relations_demo.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/comments")
@Tag(name = "Comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @Operation(summary = "Add a comment to a task")
    public ResponseEntity<CommentResponse> add(@PathVariable Long projectId, @PathVariable Long taskId,
            @Valid @RequestBody CommentCreateRequest dto) {
        CommentResponse saved = commentService.add(projectId, taskId, dto);
        URI location = URI.create("/api/projects/" + projectId + "/tasks/" + taskId + "/comments/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    @Operation(summary = "List comments of a task")
    public List<CommentResponse> list(@PathVariable Long projectId, @PathVariable Long taskId) {
        return commentService.list(projectId, taskId);
    }
}
