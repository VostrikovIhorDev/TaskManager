package com.ihor.taskmanager;

import java.time.LocalDateTime;

public class Task {

    private Integer id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;


    public Task(String title, String description,  TaskPriority priority ) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.priority = priority;
        this.createdAt = LocalDateTime.now();
    }
}

