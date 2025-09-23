package dev.cf1887.relations_demo.exception;

public class DuplicateTagNameException extends RuntimeException {
    public DuplicateTagNameException(String name) {
        super("Tag name already exists: " + name);
    }
}
