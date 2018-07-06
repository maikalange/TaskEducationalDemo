package com.qa.domain;

import java.time.LocalDateTime;

public class Task {

    private final String description;
    private final LocalDateTime dateCreated;
    private final LocalDateTime dueDate;
    private final String title;
    private final Category category;
    private final int id;

    public int getId() {
        return id;
    }

    public enum Category {
        Chores, Office, Kids, PersonalDevelopment
    };

    public Task(String description, LocalDateTime dateCreated, LocalDateTime dueDate, Category category, String title, int id) {
        this.description = description;
        this.dateCreated = dateCreated;
        this.dueDate = dueDate;
        this.category = category;
        this.title = title;
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }
}
