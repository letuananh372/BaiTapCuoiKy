/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tu
 */
public class ServerThread implements Runnable {
    private DatagramSocket socket=null;
    DatagramPacket packet;
    private InetAddress host=null;
    private int port;
    private String name;
    private String key ="baomatthongtin";
    public ServerThread(DatagramSocket socket,String name) throws IOException {
        this.socket = socket;
        this.packet=receive();
        this.host=packet.getAddress();
        this.port=packet.getPort();
        this.name = name;
    }
 public void startThread() {
        new Thread(this).start();
    }
 
  public void run(){
        String chuoi=""; // Bien de luu chuoi nhan duoc
        System.out.println("Server is Listening");
        DES des= new DES();
        try{
            while(true){//Wait for client call
                chuoi = new String(packet.getData()).trim();
                
                
                Scanner op = new Scanner(chuoi);
                op.useDelimiter("#");
                chuoi=op.next().trim();
                String connection="";
                if (!chuoi.equals("")) {
                    switch(chuoi){
                        case "SQLConnect":{
                        
                            Scanner sc = new Scanner(op.next().trim());
                            sc.useDelimiter("@");
                            String servername =  sc.next().trim();
                            String username = sc.next().trim();
                            String password = sc.next().trim();
                                                
                            DBAdress acc = new DBAdress();        
                            Connection con=acc.getConnection(servername, username, password);
                            if (con!=null) {
                                connection = "Success";
                                send(connection,host,port);
                            }else{
                                connection = "Fail";
                                send(connection,host,port);
                                
                            }
                            break;
                        }
                        case "Connect":{
                            connection = "Success";
                            send(connection,host,port);
                            break;
                        }
                        case "CreateData":{
                            String sqlconnect =op.next().trim();
                            
                            
                            
                            Scanner sc = new Scanner(sqlconnect);
                            sc.useDelimiter("@");
                            String servername =  sc.next().trim();
                            String username = sc.next().trim();
                            String password = sc.next().trim();
                            
                            DBAdress acc = new DBAdress(); 
                            Connection con=acc.getConnection(servername, username, password);
                            //boolean conencted = acc.getConnection("localhost:1433", "tu", "123456");
                            
                            String value =op.next().trim();
                            sc = new Scanner(value);
                            sc.useDelimiter("@");
                            String hoten =des.encrypt(key, sc.next().trim());
                           
                            String maso =des.encrypt(key, sc.next().trim());                            
                            
                            String diemToan =sc.next().trim();
                            String toan = des.encrypt(key,diemToan);
                            
                            String diemLy=sc.next().trim();
                            String ly =des.encrypt(key, diemLy);
                            
                            String diemAV=sc.next().trim();
                            String av = des.encrypt(key, diemAV);
                            Float Diemtb = ((Float.parseFloat(diemToan)+Float.parseFloat(diemLy)+Float.parseFloat(diemAV))/3);
                                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                            String dtb = des.encrypt(key, Float.valueOf(decimalFormat.format(Diemtb)).toString());
                            String sqlCheck = "Select * from HocVien where MSSV=?";
                            PreparedStatement ps;
                            ps =con.prepareStatement(sqlCheck);
                            ps.setString(1, maso);
                            ResultSet rs = ps.executeQuery();
                            if (!rs.next()) {
                                String sql = "insert into HocVien(MSSV,HoTen,DiemToan,DiemLy,DiemAV,DiemTB) values ('"
                                    +maso+"','"
                                    +hoten+"','"
                                    +toan+"','"
                                    +ly+"','"
                                    +av+"','"
                                    +dtb+"')";
                            
                                int kq = acc.Update(sql);
                                if (kq!=0) {
                                    connection = "Thêm Sinh Viên Thành Công";
                                    send(connection,host,port);
                                }else{
                                    connection = "Thêm Sinh Viên thất bại";
                                    send(connection,host,port);

                                }
                            }else{
                                connection = "Trùng Mã số Sinh Viên";
                                send(connection,host,port);
                                
                            }
                            
                            break;
                        }
                        case "GetAllData":{
                            
                            String sql =op.next().trim();
                            Scanner sc = new Scanner(sql);
                            sc.useDelimiter("@");
                            String servername =  sc.next().trim();
                            String username = sc.next().trim();
                            String password = sc.next().trim();
                                
                            String str="";
                            DBAdress acc = new DBAdress();
                            
                            Connection con=acc.getConnection(servername, username, password);
                            //boolean conencted = acc.getConnection("localhost:1433", "tu", "123456");
                            ResultSet result = acc.GetAllData();
                            while(result.next())
                                {
                                    str="";
                                    str+=des.decrypt(key,result.getString("MSSV"));
                                    str+="///";
                                    str+=des.decrypt(key,result.getString("HoTen"));
                                    str+="///";
                                    String diemToan =des.decrypt(key, result.getString("DiemToan"));
                                    str+=diemToan;
                                    str+="///";
                                    String diemLy =des.decrypt(key,result.getString("DiemLy"));                                   
                                    str+=diemLy;
                                    str+="///";
                                    String diemAV =des.decrypt(key,result.getString("DiemAV"));                                    
                                    str+=diemAV;
                                    str+="///";
                                    String diemTB =des.decrypt(key,result.getString("DiemTB"));     
                                    str+=diemTB;
                                    str+="///";
                                    
                                    send(str,host,port);
                                };
                                send("close",host,port);
                            
                            
                            break;
                        }
                        case "UpdateData":{
                            
                            String sqlconnect =op.next().trim();
                            
                            
                            
                            Scanner sc = new Scanner(sqlconnect);
                            sc.useDelimiter("@");
                            String servername =  sc.next().trim();
                            String username = sc.next().trim();
                            String password = sc.next().trim();
                            
                            DBAdress acc = new DBAdress(); 
                            Connection con=acc.getConnection(servername, username, password);
                            //boolean conencted = acc.getConnection("localhost:1433", "tu", "123456");
                            
                            String value =op.next().trim();
                            sc = new Scanner(value);
                            sc.useDelimiter("@");
                            String hoten =des.encrypt(key, sc.next().trim());
                           
                            String maso =des.encrypt(key, sc.next().trim());                            

                            String diemToan =sc.next().trim();
                            String toan = des.encrypt(key,diemToan);
                            
                            String diemLy=sc.next().trim();
                            String ly =des.encrypt(key, diemLy);
                            
                            String diemAV=sc.next().trim();
                            String av = des.encrypt(key, diemAV);
                            Float Diemtb = ((Float.parseFloat(diemToan)+Float.parseFloat(diemLy)+Float.parseFloat(diemAV))/3);
                                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                            String dtb = des.encrypt(key, Float.valueOf(decimalFormat.format(Diemtb)).toString());
                            
                            String sql = "Update  HocVien set HoTen='"+hoten+"',DiemToan='"+toan+"',DiemLy='"+ly+"',DiemAV='"
                                    +av+"',DiemTB='"+dtb+"' Where MSSV='"+maso+"'";
                                    
                            
                            int kq = acc.Update(sql);
                            if (kq!=0) {
                                connection = "Cập nhật dữ liệu thành công";
                                send(connection,host,port);
                            }else{
                                connection = "Cập nhật dữ liệu thất bại";
                                send(connection,host,port);
                                
                            }
                            
                            
                            break;
                        }
                        case "RemoveData":{
                            String sqlconnect =op.next().trim();
                            
                            
                            
                            Scanner sc = new Scanner(sqlconnect);
                            sc.useDelimiter("@");
                            String servername =  sc.next().trim();
                            String username = sc.next().trim();
                            String password = sc.next().trim();
                            
                            DBAdress acc = new DBAdress(); 
                            Connection con=acc.getConnection(servername, username, password);
                            //boolean conencted = acc.getConnection("localhost:1433", "tu", "123456");
                            
                            String value = des.encrypt(key,op.next().trim());
                          
                            String sql ="Delete from HocVien where MSSV='"+value+"'";
                           
                           
                            int kq = acc.Update(sql);
                            if (kq!=0) {
                                connection = "Xóa dữ liệu thành công";
                                send(connection,host,port);
                            }else{
                                connection = "Xóa dữ liệu thất bại";
                                send(connection,host,port);
                                
                            }
                            break;
                        }
                        default: {
                            connection = "Không tìm thấy lệnh ";
                            send(connection,host,port);
                                
                        }
                    }    
                    
                    
                }
           
            }
        }catch(Exception e){
            e.printStackTrace();
        } catch (Throwable ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            socket.close();
        }
    
    }
    
    private void send(String chuoi,InetAddress host,int port) throws IOException{
        byte[] buffer =chuoi.getBytes();//Chuyen thanh bytes
        //Dua vao goi tin
        DatagramPacket packet = new DatagramPacket(buffer,buffer.length,host,port);
        socket.send(packet);
        
    }
       private DatagramPacket receive() throws IOException {
        byte[] buffer = new byte[65507];
        DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
        socket.receive(packet);
        return packet;
        
    }
  

}