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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public final class DbService {

    private static final String CONNECTION_URL = "jdbc:derby://localhost:1527/TasksDb;user=task_user;password=task_user";
    private static  Connection conn;
    
    public static boolean save(Task task) {
        try {
            return initialiseDatabase(task);
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DbService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean delete(int id){
        String sql  = "DELETE Tasks WHERE Id=?";
        
        return false;
    }
    
    private static boolean initialiseDatabase(Task task) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        conn = DriverManager.getConnection(CONNECTION_URL);
        String insertSql = "INSERT INTO TASK_USER.\"Tasks\"(\"Title\",\"Description\",\"Category\",\"DateCreated\",\"DueDate\")Values(?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(insertSql);


        statement.setString(1, task.getTitle());
        statement.setString(2, task.getDescription());
        statement.setString(3, task.getCategory().toString());
        
        statement.setDate(4, java.sql.Date.valueOf(task.getDateCreated().toLocalDate()));
        statement.setDate(5, java.sql.Date.valueOf(task.getDueDate().toLocalDate()));

        statement.closeOnCompletion();
        return statement.execute();
        
    }
}
