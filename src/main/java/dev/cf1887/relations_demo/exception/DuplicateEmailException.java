package dev.cf1887.relations_demo.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("User email already exists: " + email);
    }
}
