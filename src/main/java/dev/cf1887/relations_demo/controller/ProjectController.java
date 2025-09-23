package dev.cf1887.relations_demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cf1887.relations_demo.dto.ProjectCreateRequest;
import dev.cf1887.relations_demo.dto.ProjectResponse;
import dev.cf1887.relations_demo.entity.Project;
import dev.cf1887.relations_demo.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService theProjectService) {
        this.projectService = theProjectService;
    }

    /**
     * Post endpoint for creating a new project
     * 
     * @param request
     * @see ProjectCreateRequest
     * @return
     */
    @PostMapping
    @Operation(summary = "Create a project")
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectCreateRequest dto) {
        // assign the projectService to this save operation
        Project saved = projectService.create(dto);
        // generate a proper success message
        URI location = URI.create("/api/projects/" + saved.getId());
        return ResponseEntity.created(location).body(new ProjectResponse(saved.getId(), saved.getName()));
    }

    /**
     * Get endpoint to get all projects
     * 
     * @return
     */
    @GetMapping
    @Operation(summary = "List all projects")
    public List<ProjectResponse> list() {
        return projectService.list();
    }
}
