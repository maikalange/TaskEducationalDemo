/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qa.services;

import com.qa.domain.Task;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Administrator
 */
public final class DbService {    
    private static Connection conn;
    private static ClientDriver driver;

    private static void initialiseDbConnection() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        final String CONNECTION_URL = "jdbc:derby://localhost:1527/TasksDb;user=task_user;password=task_user";
        if (driver == null) {
            driver = (ClientDriver) Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        }
        if (conn == null) {
            conn = DriverManager.getConnection(CONNECTION_URL);
        }
    }
    
    public Task getTaskById(int id){
        Task task=null;
        String findSQL  = "SELECT * FROM TASK_USER.\"Tasks\" WHERE ID  = ?;";
        try {
            PreparedStatement statement  = conn.prepareStatement(findSQL);
            ResultSet results = statement.executeQuery();
            while(results.next()){
                String title  = results.getString("Title");
                String description  = results.getString("Description");
                String category  = results.getString("Category");
                
                LocalDateTime dueDate  = LocalDateTime.parse(results.getString("DueDate"));
                LocalDateTime dateCreated  = LocalDateTime.parse(results.getString("DateCreated"));
                task  = new Task(description,dateCreated, dueDate, Task.Category.valueOf(category), title);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbService.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return task;
    }

    public static boolean save(Task task) {
        boolean success = false;
        try {
            initialiseDbConnection();
            String insertSql = "INSERT INTO TASK_USER.\"Tasks\"(\"Title\",\"Description\",\"Category\",\"DateCreated\",\"DueDate\")Values(?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(insertSql);

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getCategory().toString());

            statement.setDate(4, java.sql.Date.valueOf(task.getDateCreated().toLocalDate()));
            statement.setDate(5, java.sql.Date.valueOf(task.getDueDate().toLocalDate()));
            statement.execute();

            statement.closeOnCompletion();
            success = true;

        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DbService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public static boolean delete(int id) {
        String sql = "DELETE Tasks WHERE Id=?";

        return false;
    }

}
