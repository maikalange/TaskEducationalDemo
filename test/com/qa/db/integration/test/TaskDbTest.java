package com.qa.db.integration.test;
import org.junit.*;
import com.qa.domain.Task;
import com.qa.services.DbService;
import java.time.LocalDateTime;

import org.junit.Test;
public class TaskDbTest {

    @Test
    public void testSaveTask() {
        String description = " This is a test task";
        String title = "Testing Save";
        Task task = new Task(description, LocalDateTime.now(), LocalDateTime.now().plusDays(2), Task.Category.Chores, title,0);
        
        boolean isSaved  = DbService.save(task);
       Assert.assertTrue(isSaved);
    }
    
    public void testDeleteTask(){
        
    }

}
