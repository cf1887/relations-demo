package dev.cf1887.relations_demo.exception;

public class DuplicateProjectNameException extends RuntimeException {
    public DuplicateProjectNameException(String name) {
        super("Project name already exists: " + name);
    }
}
