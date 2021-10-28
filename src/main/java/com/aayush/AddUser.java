package com.aayush;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.sql.ResultSet;
// import java.sql.*;

public class AddUser extends JFrame implements ActionListener,FocusListener,KeyListener{
    
    JLabel li;
    JLabel[] notify = new JLabel[3];
    JLabel[] arr = new JLabel[3];
    String[] labels = {"SetUp LoginId", "Set Password" , "Confirm Password"};
    JButton back,signUp,addMember;
    // JTextField[] tarr = new JTextField[5];
    PlaceholderTextField tarr;
    JPasswordField[] parr = new JPasswordField[2];
    // Store temp member values
    String[][] tempStorage;
    String[] tempMainStorage = new String[4];
    
  //members  
    int memCounter=2;
    int numMem;
    int t=0;
    String headStr;
//end
    Font forLabel = new Font("Tahoma" , Font.ITALIC , 17 );
    Font headFont = new Font("Serif" , Font.ITALIC , 32);
    int i;
    
    
    AddUser(){

        
        JLabel head = new JLabel("<html><u>Register</u></html>");
        // head.setFont(new Font("Serif" , Font.ITALIC , 32));
        head.setFont(headFont);
        head.setBounds(360,5,200,40);
        add(head);
        //connect db
        

        
        int x=60;
        for(i=0;i<3;i++){
            arr[i]= new JLabel(labels[i]);
            arr[i].setFont(forLabel);
            arr[i].setBounds(20,x,150,30);
            add(arr[i]);
            if(i==0){
                tarr = new PlaceholderTextField();
                tarr.setBounds(175,x,150,30);
                tarr.setFont(forLabel);
                add(tarr);
                x+=50;
            }else{
                parr[i-1] = new JPasswordField();
                parr[i-1].setBounds(175,x,150,30);
                parr[i-1].addFocusListener(this);
                parr[i-1].setFont(forLabel);
                add(parr[i-1]);
                x+=50;
            }
            x-=20;
            notify[i] = new JLabel("username available :)");
            notify[i].setBounds(10,x,300,12);
            notify[i].setForeground(new Color(0, 153, 51));
            add(notify[i]);
            notify[i].setVisible(false);
            x+=20;
            // x+=50;
        }
            x-=20;
            notify[2] = new JLabel("username available :)");
            notify[2].setBounds(10,x,300,12);
            notify[2].setForeground(new Color(0, 153, 51));

            add(notify[2]);
            notify[2].setVisible(false);
            x+=30;

        
        tarr.addFocusListener(this);
        parr[1].addKeyListener(this);
            


        //SignUp button
        back = new JButton("<html><u>Back</u></html>");
        back.setFont(new Font("Serif" , Font.ITALIC , 21));
        back.setForeground(Color.WHITE);
        back.setBackground(Color.BLACK);
        back.setBounds(40,x,100,30);
        back.addActionListener(this);
        add(back);

        signUp = new JButton("<html><u>signUp</u></html>");
        signUp.setFont(new Font("Serif" , Font.ITALIC , 21));
        signUp.setForeground(Color.WHITE);
        signUp.setBackground(Color.BLACK);
        signUp.setBounds(170,x,120,30);
        signUp.addActionListener(this);
        add(signUp);


        //Image
        ImageIcon i1 = new ImageIcon(this.getClass().getResource("/com/aayush/icons/ocean.jpg"));
        Image i2 = i1.getImage().getScaledInstance(400,380,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        li = new JLabel(i3);
        li.setBounds(350,60,400,380);
        add(li);
        
        //mainPane
        setBounds(400,170,780,500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
    }


//addMember function
    public void actionPerformed (ActionEvent ae){
        if( signUp == ae.getSource()){
            Conn c = new Conn();
        //login table    
            Hash h = new Hash();
            String tPassword = new String(parr[0].getPassword());
            String hashedPassword = h.doHashing(tPassword);            
            String query2 = "insert into user values('"+tarr.getText() +"','"+hashedPassword+"')";
            try{
                System.out.println(query2);
                c.s.executeUpdate(query2);//login
                JOptionPane.showMessageDialog(null , "Head added sucessfully");
                this.setVisible(false);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null , "Error registering.Please try again :)");
                e.printStackTrace();
            }
        }else if( back == ae.getSource()){
            System.out.println("Signed IN");
            this.setVisible(false);
            // new App().setVisible(true);
        }
    }


    public void focusGained(FocusEvent fe){
        
    }
    public void focusLost(FocusEvent fe){
        if(fe.getSource() == tarr){
            //check with db if this is available i.e. unique
            Conn c = new Conn();
            String usrnm = tarr.getText();
            String query = "select COUNT(*) from user where username='"+usrnm+"'";
            try{
                ResultSet rs = c.s.executeQuery(query);
                rs.next();
                
                
                if(rs.getInt(1) != 0){
                    notify[0].setText("username already exists :(");
                    notify[0].setForeground(new Color(153,0,0));
                    // notify[0].requestFocus();
                }else{
                    notify[0].setText("username available :)");
                    notify[0].setForeground(new Color(0, 153, 51));
                }
                notify[0].setVisible(true);
            }catch(Exception e){
                System.out.println("Error in login check db conn");
                // e.printStackTrace();
            }
        }else if(parr[0] == fe.getSource()){
            String pass = new String(parr[0].getPassword());
            PasswordValidator pv = new PasswordValidator();
            try{
                pv.isValid(pass);
                notify[1].setText("Password valid");
                notify[1].setForeground(new Color(0, 153, 51));
            }catch(InvalidPasswordException e){
                notify[1].setText(e.printMessage());
                notify[1].setForeground(new Color(153,0,0));
                // notify[0].requestFocus();
            }
            notify[1].setVisible(true);
        }
    }


    public void keyPressed(KeyEvent e) {}  
    public void keyReleased(KeyEvent e) {  
        String pas1 = new String(parr[0].getPassword());
        String pas2 = new String(parr[1].getPassword());
        
        if(pas1.equals(pas2)){
            notify[2].setText("Password matches");
            notify[2].setForeground(new Color(0, 153, 51));
        }else{
            notify[2].setText("Password doesn't match");
            notify[2].setForeground(new Color(153,0,0));
        }
        notify[2].setVisible(true);
    }  
    public void keyTyped(KeyEvent e) {}  

    public static void main(String[] args) {
        new AddUser().setVisible(true);
    }
}

