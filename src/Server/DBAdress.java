package Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Client.frmDuLieu;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author Tu
 */

public class DBAdress {
    
    private Connection con;
    private Statement stmt;
    public DBAdress(){
        
        
    }
    
    public Connection getConnection(String server,String username,String password){
        try{
            MyConnection mycon = new MyConnection();
            con = mycon.getConnection(server,username,password);     
            con = mycon.getConnection();
            stmt=con.createStatement();
            return con;
          

        }catch(Exception ex){
            return null;
              
        }
    }
    
    public int Update(String str){
        try{
            int i= stmt.executeUpdate(str);
            return i;
        }catch(Exception ex){
            System.out.print(ex.toString());
            return -1;
        }
    }
    
    
    public ResultSet Query(String str){
        try{
            ResultSet rs = stmt.executeQuery(str);
            return rs;
        }catch(Exception ex){
            System.out.print(ex.toString());
            return null;
        }
    }
    
    
    public ResultSet GetAllData(){
        try{
            ResultSet rs = stmt.executeQuery("select * from HocVien");
            return rs;
        }catch(Exception ex){
            return null;
        }
    }
    
}

