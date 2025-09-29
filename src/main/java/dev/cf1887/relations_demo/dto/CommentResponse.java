package dev.cf1887.relations_demo.dto;

import java.time.Instant;

public class CommentResponse {

    private Long id;

    private String content;

    private Instant createdAt;

    public CommentResponse() {
    }

    public CommentResponse(Long id, String content, Instant createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
