package com.aayush;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    // public static void main(String[] args){
    //     System.out.println(doHashing("12345678"));
    // }
    public  String doHashing(String password ){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] resultArr = messageDigest.digest();
            StringBuilder s = new StringBuilder();
            for(byte b : resultArr){
                s.append(String.format("%02x", b));
            }
            return s.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
}
