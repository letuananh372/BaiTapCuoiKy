/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.JOptionPane;

/**
 *
 * @author Tu
 */
public class DES {
    
    private  String text;
    private byte[] byt;
    /* private static void doCopy(String input) throws Throwable{
    
    int kq =acc.Update(input);
    if(kq!=0)
    JOptionPane.showMessageDialog(null, "Thành công!!! ");
    else
    JOptionPane.showMessageDialog(null, "Thất bại !!! ");
    
    }*/
    
    
    private  void encryptOrDecrypt(String key, int mode,String text) throws Throwable {
    
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf= SecretKeyFactory.getInstance("DES");
        SecretKey desKey =skf.generateSecret(dks);
        Cipher cipher= Cipher.getInstance("DES");
        if (mode==Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE,desKey);
            
            byte[] byteEncrypted = cipher.doFinal(text.getBytes());
            String encrypted =Base64.getEncoder().encodeToString(byteEncrypted);
            this.text=encrypted;
        }else if (mode==Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE,desKey);
            System.out.print(text);
            byte[] byteDecrypted = cipher.doFinal(Base64.getDecoder().decode(text));
            String decrypted = new String(byteDecrypted);
            this.text=decrypted;
        }
            
        
    }
    
    
    public String decrypt(String key,String decrypt) throws Throwable{
        encryptOrDecrypt(key,Cipher.DECRYPT_MODE, decrypt);
        return text;
    } 
    
    public String encrypt(String key,String encrypte) throws Throwable{
        encryptOrDecrypt(key,Cipher.ENCRYPT_MODE,encrypte);
        return text;
    }

   

    public String getText() {
        return text;
    }
    
}
