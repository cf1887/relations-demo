package dev.cf1887.relations_demo.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cf1887.relations_demo.dto.UserCreateRequest;
import dev.cf1887.relations_demo.dto.UserProfileCreateRequest;
import dev.cf1887.relations_demo.dto.UserProfileResponse;
import dev.cf1887.relations_demo.dto.UserResponse;
import dev.cf1887.relations_demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest dto) {
        UserResponse saved = userService.createUser(dto);
        URI location = URI.create("/api/users/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PostMapping("/{userId}/profile")
    @Operation(summary = "Create a profile for a user")
    public ResponseEntity<UserProfileResponse> createProfile(@PathVariable Long userId,
            @Valid @RequestBody UserProfileCreateRequest dto) {
        UserProfileResponse saved = userService.createProfile(userId, dto);
        URI location = URI.create("/api/users/" + userId);
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get a user (and the referring profile if exists)")
    public UserResponse get(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

}
