package dev.cf1887.relations_demo.dto;

public class TaskResponse {
    
    private Long id;

    private String title;

    private Long projectId;

    public TaskResponse() {
    }

    public TaskResponse(Long id, String title, Long projectId) {
        this.id = id;
        this.title = title;
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    
}
