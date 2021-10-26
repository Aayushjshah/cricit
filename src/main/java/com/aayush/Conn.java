package com.aayush;

import java.sql.*;

public class Conn {
    Connection c;
    Statement s;
    Conn(){
        try{
            c=DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/cricit","root","root");

            s=c.createStatement();

        }catch(Exception e){
            System.out.println("Error in db connection");
            e.printStackTrace();
        }
    }
//test dbConnection    (change )    
    //     public static void main( String[] args )
    // {
    //     Conn c = new Conn();
    //     try{
    //         ResultSet rs = c.s.executeQuery("select policyId from policies");
    //         while(rs.next()){
    //             System.out.println( rs.getString(1) );
    //         }
    //     }catch(Exception e){
    //         System.out.println( "Hello World!" );
    //         e.printStackTrace();
    //     }
    // }       
}

