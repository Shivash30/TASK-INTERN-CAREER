package connect;


import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Shivash Jurakan
 */
public class ConnectDB {
    public java.sql.Connection getConnection() throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(Exception e){
            
        }
        String url = "jdbc:mysql://localhost:3306/exam_app?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "root";
        String password = "Shivash30";
        
        return DriverManager.getConnection(url, username, password);
    }
}
