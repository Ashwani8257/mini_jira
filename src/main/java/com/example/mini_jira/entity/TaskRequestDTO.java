package com.example.mini_jira.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.example.mini_jira.entity.Task.TaskStatus;
public class TaskRequestDTO {
    private String title;

    private TaskStatus status;
    private Long assignedToId;



    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }
}
