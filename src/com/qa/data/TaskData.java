package com.qa.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.qa.domain.Task;
import com.qa.domain.Task.Category;

public class TaskData {

    private static Collection<Task> tasks = new ArrayList<Task>();

    private static Collection<Task> loadTasks() {
        tasks.add(new Task("Tennis practice with Marcus", LocalDateTime.now(), LocalDateTime.now().plusDays(5), Category.Kids, "Sports", 1));
        tasks.add(new Task("Go to the park", LocalDateTime.now(), LocalDateTime.now().plusHours(2), Category.Kids, "Play with Kids", 2));
        tasks.add(new Task("Wash car", LocalDateTime.now(), LocalDateTime.now().plusHours(2), Category.Chores, "Daily Chores", 3));
        tasks.add(new Task("Team Lunch", LocalDateTime.now(), LocalDateTime.now().plusHours(3), Category.Office, "Team Building", 4));
        tasks.add(new Task("Paint hallway", LocalDateTime.now(), LocalDateTime.now().plusDays(2), Category.Chores, "Decorating", 5));
        tasks.add(new Task("Prepare training for Java", LocalDateTime.now(), LocalDateTime.now().plusDays(2),
                Category.Office, " Training Java", 6));

        return tasks;
    }

    public static Collection<Task> getTasks() {
        return loadTasks();
    }

}
