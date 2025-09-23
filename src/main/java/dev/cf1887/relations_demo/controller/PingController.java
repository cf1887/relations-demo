package dev.cf1887.relations_demo.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Health/Ping")
public class PingController {

    @GetMapping("/ping")
    @Operation(summary = "Health check", description = "Simple endpoint for testing")
    public ResponseEntity<Map<String, Object>> ping() {
        return ResponseEntity.ok(
                Map.of(
                        "status", "ok",
                        "app", "relations-demo",
                        "timestamp", Instant.now().toString()));
    }

}
