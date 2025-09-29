package dev.cf1887.relations_demo.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.cf1887.relations_demo.dao.UserProfileRepository;
import dev.cf1887.relations_demo.dao.UserRepository;
import dev.cf1887.relations_demo.dto.UserCreateRequest;
import dev.cf1887.relations_demo.dto.UserProfileCreateRequest;
import dev.cf1887.relations_demo.dto.UserProfileResponse;
import dev.cf1887.relations_demo.dto.UserResponse;
import dev.cf1887.relations_demo.entity.User;
import dev.cf1887.relations_demo.entity.UserProfile;
import dev.cf1887.relations_demo.exception.DuplicateEmailException;
import dev.cf1887.relations_demo.exception.NotFoundException;
import dev.cf1887.relations_demo.exception.ProfileAlreadyExistsException;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    public UserResponse createUser(UserCreateRequest dto) {
        final String email = dto.getEmail().trim().toLowerCase();
        // Check if email already exists beforehand
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
        try {
            // Create user instance
            User tempUser = new User();
            tempUser.setEmail(email);
            // Save user instance to database
            User saved = userRepository.save(tempUser);
            return new UserResponse(saved.getId(), saved.getEmail(), null);
        } catch (DataIntegrityViolationException exception) {
            // Fallback in case of a race condition
            throw new DuplicateEmailException(email);
        }
    }

    @Transactional
    public UserProfileResponse createProfile(Long userId, UserProfileCreateRequest dto) {
        // Search for the user
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found: " + userId);
        }
        User user = userOptional.get();
        // Ensure that the user does not already have a profile
        if (userProfileRepository.existsByUser_Id(userId)) {
            throw new ProfileAlreadyExistsException(userId);
        }
        // Create UserProfile instance
        UserProfile tempProfile = new UserProfile();
        tempProfile.setFirstName(dto.getFirstName().trim());
        tempProfile.setLastName(dto.getLastName().trim());
        tempProfile.setUser(user);
        // Bidirectional relationship
        user.setProfile(tempProfile);
        // Save UserProfile instance to database
        UserProfile saved = userProfileRepository.save(tempProfile);
        return new UserProfileResponse(saved.getId(), saved.getFirstName(), saved.getLastName());
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {
        // Search for the user
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found: " + userId);
        }
        User user = userOptional.get();
        // Get the referring UserProfile
        UserProfile userProfile = user.getProfile();
        UserProfileResponse userProfileResponse = userProfile == null ? null : new UserProfileResponse(userProfile.getId(), userProfile.getFirstName(), userProfile.getLastName());
        return new UserResponse(user.getId(), user.getEmail(), userProfileResponse);
    }
}
