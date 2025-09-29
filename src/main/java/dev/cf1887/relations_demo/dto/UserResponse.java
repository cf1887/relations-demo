package dev.cf1887.relations_demo.dto;

public class UserResponse {

    private Long id;

    private String email;

    private UserProfileResponse profileDto;

    public UserResponse() {
    }

    public UserResponse(Long id, String email, UserProfileResponse profileDto) {
        this.id = id;
        this.email = email;
        this.profileDto = profileDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserProfileResponse getProfileDto() {
        return profileDto;
    }

    public void setProfileDto(UserProfileResponse profileDto) {
        this.profileDto = profileDto;
    }
}
