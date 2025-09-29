package dev.cf1887.relations_demo.exception;

public class ProfileAlreadyExistsException extends RuntimeException {

    public ProfileAlreadyExistsException(Long userId) {
        super("Profile already exists for user: " + userId);
    }
}
