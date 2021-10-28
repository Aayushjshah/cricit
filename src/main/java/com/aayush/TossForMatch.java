package com.aayush;
import javax.swing.*;
import java.awt.Color;
// import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TossForMatch extends JFrame implements ActionListener{
    int match_id;
    int toss_won;
    int batting_team_id,team1_id,team2_id;
    JButton team1,team2,bat,bowl;
    JLabel question2,question;
    FontPicker fp = new FontPicker();
    TossForMatch(int match_id){
        this.match_id=match_id;
        String team1_name="",team2_name="";
        try{
            Conn c= new Conn();
            String query="select team1_id,team2_id,team1_name,team2_name from matches where match_id="+match_id;
            ResultSet rs = c.s.executeQuery(query);
            rs.next();
            team1_id=rs.getInt(1);
            team2_id=rs.getInt(2);
            team1_name=rs.getString(3);
            team2_name=rs.getString(4);
        }catch(Exception e){
            e.printStackTrace();
        }
        question = new JLabel("Toss Won by?");
        question.setBounds(12,25,300,30);
        question.setFont(fp.eCopyFont);
        add(question);

        team1 = new JButton(team1_name);
        team1.setFont(fp.forLabel);
        team1.setForeground(Color.WHITE);
        team1.setBackground(Color.BLACK);
        team1.addActionListener(this);
        team1.setBounds(20,130,200,30);
        add(team1);

        team2 = new JButton(team2_name);
        team2.setFont(fp.forLabel);
        team2.setForeground(Color.WHITE);
        team2.setBackground(Color.BLACK);
        team2.addActionListener(this);
        team2.setBounds(250,130,200,30);
        add(team2);
//part 2.
question2 = new JLabel("Choose to");
question2.setBounds(12,25,300,30);
question2.setFont(fp.eCopyFont);
add(question2);
question2.setVisible(false);

bat = new JButton("Bat");
bat.setFont(fp.forLabel);
bat.setForeground(Color.WHITE);
bat.setBackground(Color.BLACK);
bat.addActionListener(this);
bat.setBounds(20,130,200,30);
add(bat);
bat.setVisible(false);

bowl = new JButton("Bowl");
bowl.setFont(fp.forLabel);
bowl.setForeground(Color.WHITE);
bowl.setBackground(Color.BLACK);
bowl.addActionListener(this);
bowl.setBounds(250,130,200,30);
add(bowl);
bowl.setVisible(false);


        //main Pane
        setBounds(590,350,500,200);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new TossForMatch(3);
    }
    public void toggle(boolean q2){
        question.setVisible(!q2);
        team1.setVisible(!q2);
        team2.setVisible(!q2);

        question2.setVisible(q2);
        bat.setVisible(q2);
        bowl.setVisible(q2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==team1){
            toss_won=team1_id;
            batting_team_id=team2_id;
            this.toggle(true);
        }else if(e.getSource()==team2){
            toss_won=team2_id;
            batting_team_id=team1_id;
            this.toggle(true);
        }else if(e.getSource()==bat){
            batting_team_id=toss_won;
            
            //update to db
            String tossUpdate="update matches set toss="+toss_won+" ,bat_first="+batting_team_id+" where match_id="+match_id;
            System.out.println(tossUpdate);
        }else{
            //render to Scorematch
            String tossUpdate="update matches set toss="+toss_won+" ,bat_first="+batting_team_id+" where match_id="+match_id;
            System.out.println(tossUpdate);
        }
        
    }
}
