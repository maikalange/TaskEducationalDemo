package com.qa.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import com.qa.data.TaskData;
import com.qa.domain.Task;
import com.qa.domain.Task.Category;

public final class TaskRepository {
	private static final Collection<Task> tasks = TaskData.getTasks();

	public static Collection<Task> findTaskByCategory(Category category) {
		return tasks.stream().filter(t -> t.getCategory() == category).collect(Collectors.toList());
	}

	public static Collection<Task> findOverdueTasks() {
		return tasks.stream().filter(t -> t.getDueDate().isBefore(LocalDateTime.now())).collect(Collectors.toList());
	}
}
