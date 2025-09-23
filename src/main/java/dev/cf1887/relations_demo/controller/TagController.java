package dev.cf1887.relations_demo.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cf1887.relations_demo.dto.TagCreateRequest;
import dev.cf1887.relations_demo.dto.TagResponse;
import dev.cf1887.relations_demo.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tags")
@Tag(name = "Tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @Operation(summary = "Create a tag")
    public ResponseEntity<TagResponse> create(@Valid @RequestBody TagCreateRequest dto) {
        TagResponse saved = tagService.create(dto);
        URI location = URI.create("/api/tags/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }
}
