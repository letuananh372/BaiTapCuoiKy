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
import java.util.Scanner;

/**
 *
 * @author Tu
 */
public class UDPServer {
    private DatagramSocket socket = null; //Khai bao DataGramSocket de ket noi
    static final int PORT = 1234;// Khai bao Port su dung
   
    public UDPServer(){
        try{
            socket = new  DatagramSocket(PORT);
        }catch(Exception e){
            e.printStackTrace();//hiển thị lỗi ở bản điều kiển
        }
            
    }
    
    public void action(){
        int i=0;
        System.out.println("Serverlistening...");
        try{
            while(true){
                ServerThread thread = new ServerThread(socket,"Client#"+i);
                
                thread.startThread();
                System.out.printf("Thread for Client#%d generating...%n",i++);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
   
    
    public static void main(String []args){
        new UDPServer().action();
    }
    
}
