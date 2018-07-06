/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qa.services;

import com.qa.domain.Task;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public List<Task> findTasksByCategory(Task.Category category) {
        String sql = "SELECT ID,TITLE,DESCRIPTION,DUEDATE,DATECREATED FROM TASK_USER.\"Tasks\"     where \"Category\" = '?'";
        List<Task> tasks = new ArrayList<>();
        try {
            initialiseDbConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, category.name());
            ResultSet results = statement.executeQuery();
            Task t;
            while (results.next()) {
                String title = results.getString("title");
                String description = results.getString("description");

                LocalDateTime dueDate = LocalDateTime.parse(results.getString("dueDate"));
                LocalDateTime dateCreated = LocalDateTime.parse(results.getString("dateCreated"));
                int id = results.getInt("Id");

                t = new Task(description, dateCreated, dueDate, category, title, 0);
                tasks.add(t);
            }
            conn = null;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DbService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tasks;
    }

    public boolean updateTask(Task task) {
        boolean success = false;
        try {
            String updateSQL = "UPDATE TASK_USER.\"Tasks\"  SET     \"Category\" = '?',  \"Title\" ='?',  \"Description\" ='?',  \"DueDate\" ='?' ,\"DateCreated\" ='?'  WHERE Id=?;";
            PreparedStatement statement = conn.prepareStatement(updateSQL);
            statement.setString(1, task.getCategory().name());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getDescription());

            statement.setDate(4, Date.valueOf(task.getDueDate().toLocalDate()));
            statement.setDate(5, Date.valueOf(task.getDateCreated().toLocalDate()));
            statement.setInt(6, task.getId());

            statement.setString(7, updateSQL);
            statement.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            Logger.getLogger(DbService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public Task findTaskById(int id) {

        Task task = null;
        String findSQL = "SELECT Title,Description,DueDate,DateCreated,Category FROM TASK_USER.\"Tasks\" WHERE ID  = ?;";
        try {
            initialiseDbConnection();
            PreparedStatement statement = conn.prepareStatement(findSQL);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                String title = results.getString("Title");
                String description = results.getString("Description");
                String category = results.getString("Category");

                LocalDateTime dueDate = LocalDateTime.parse(results.getString("DueDate"));
                LocalDateTime dateCreated = LocalDateTime.parse(results.getString("DateCreated"));
                task = new Task(description, dateCreated, dueDate, Task.Category.valueOf(category), title, id);
            }
            conn = null;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
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
            conn = null;
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DbService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public static boolean delete(int id) {
        String sql = "Delete FROM TASK_USER.\"Tasks\" where id =?;\"";
        boolean isSuccess  = false;
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            isSuccess =  true;
        } catch (SQLException ex) {
            Logger.getLogger(DbService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isSuccess;
    }
}
