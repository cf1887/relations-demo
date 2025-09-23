package dev.cf1887.relations_demo.dto;

public class ProjectResponse {

    private Long id;

    private String name;

    public ProjectResponse() {
    }

    public ProjectResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
