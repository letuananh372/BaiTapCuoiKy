package Server;


import java.sql.*;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tu
 */
public class MyConnection {
public Connection getConnection(){
    try{
        //Class.forName("com.mysql.jdbc.Driver");
        //String URL = "jdbc:mysql://localhost/quanlytaikhoan?user=root&passwrod=";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;"
                + "Database=quanlytaikhoan;"
                + "user=TuanAnh;"
                + "password=347553";
        Connection con = DriverManager.getConnection(URL);
        return con;

    }catch (Exception ex){
        JOptionPane.showMessageDialog(null, ex.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        return null;
    }
}
     
    public Connection getConnection(String port,String username,String password){
    try{
            //Class.forName("com.mysql.jdbc.Driver");
            //String URL = "jdbc:mysql://localhost/quanlytaikhoan?user=root&passwrod=";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String URL = "jdbc:sqlserver://"+port
                    +";Database=quanlytaikhoan;"
                    + "user="+username
                    +";password="+password;
            Connection con = DriverManager.getConnection(URL);
            return con;

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(),"Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }    

  
}


