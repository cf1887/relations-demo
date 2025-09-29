package dev.cf1887.relations_demo.exceptionHandler;

import java.time.Instant;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.cf1887.relations_demo.exception.DuplicateEmailException;
import dev.cf1887.relations_demo.exception.DuplicateProjectNameException;
import dev.cf1887.relations_demo.exception.DuplicateTagNameException;
import dev.cf1887.relations_demo.exception.NotFoundException;
import dev.cf1887.relations_demo.exception.ProfileAlreadyExistsException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateEmail(RuntimeException exception) {
        return handleUserConflicts(exception);
    }

    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleProfileAlreadyExists(RuntimeException exception) {
        return handleUserConflicts(exception);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", 404,
                        "error", "Not Found",
                        "message", exception.getMessage()));
    }

    @ExceptionHandler(DuplicateTagNameException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateTagNameException exception) {
        return conflict(exception.getMessage());
    }

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

    private ResponseEntity<Map<String, Object>> handleUserConflicts(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", 409,
                        "error", "Conflict",
                        "message", exception.getMessage()));
    }
}
