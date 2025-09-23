package dev.cf1887.relations_demo.exceptionHandler;

import java.time.Instant;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.cf1887.relations_demo.exception.DuplicateProjectNameException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DuplicateProjectNameException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateProjectNameException exception) {
        return conflict(exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleUniqueContraint(DataIntegrityViolationException exception) {
        return conflict("Project name already exists.");
    }

    private ResponseEntity<Map<String, Object>> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", 409,
                        "error", "Conflict",
                        "message", message));
    }
}
