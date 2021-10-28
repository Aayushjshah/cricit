package com.aayush;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.Image;
import javax.swing.JOptionPane;
import java.util.ArrayList;


public class AddTeam extends JPanel implements ActionListener{
    public JPanel myPanel = new JPanel();
    public JScrollPane jsp = new JScrollPane(myPanel);
//Addpolicy
    JLabel arr,arr2;
    JScrollPane tablesp;
    // String labels ={"Tournament Name","Format","Total Teams" , "Year"};
    // ArrayList
    JTable jt;
    String[] colName = {"SrNo.","Team Alias","Team Name","team_id"};   
     JTextField tarr,tarr2;
    JButton back,next;
    JLabel image;
    FontPicker fp = new FontPicker();
    int i;
    int tour_id;
    int noOfTeams;
CardLayoutMgr clm;
    private void updateTable(){
        jt = new JTable(this.getTeamsData(tour_id),colName);
        // jt = new JTable(colName,colName);
        JTableHeader header = jt.getTableHeader();
        header.setFont(fp.subHeadFont);
        header.setBackground(new Color(230, 255, 230));
        tablesp = new JScrollPane(jt);
        // tablesp.setBackground(Color);
        myPanel.add(tablesp);
        tablesp.setBounds(340,80,450,270);
       jt.setRowHeight(30);
       jt.setFont(fp.forLabel);
       tablesp.setBorder(new EmptyBorder(0,0,0,0));
       jt.setBackground(Color.GREEN);
    }
    AddTeam(CardLayoutMgr clm,int tour_id,int noOfTeams){
       //inital db connect to fill up dropdowns
       this.clm=clm;
       this.tour_id=tour_id;
       this.noOfTeams=noOfTeams;
       String tourName="";
       Conn c = new Conn();
       
    //    myPanel.add(jt);
       try{
           //dbConnect
           //forTourConnect
           ResultSet rs = c.s.executeQuery("select name from tournament where tour_id="+tour_id);
           rs.next();
            tourName=rs.getString(1);
           //forTeamsDisplay 

       }catch(Exception e){
           System.out.println("Initial connect AddPolicy");
       }
//panel Settings
        myPanel.setLayout(null);
        myPanel.setBackground(Color.WHITE);
        jsp.setBorder(new EmptyBorder(0,0,0,0));

//subItems
       updateTable();
        JLabel head = new JLabel("<html><u>Add Teams</u></html>");
        head.setBounds(240,5,400,40);
        head.setFont(fp.headFont);
        head.setForeground(Color.BLACK);
        myPanel.add(head);

        JLabel Tournament = new JLabel("<html><u>Tournament:"+tourName+"</u></html>");
        Tournament.setBounds(40,100,400,30);
        Tournament.setFont(fp.subHeadFont);
        Tournament.setForeground(Color.BLACK);
        myPanel.add(Tournament);

        int x=160;
        // for(i=0;i<4;i++){
            arr=new JLabel("Team Name");
            arr.setFont(fp.forLabel);
            arr.setForeground(Color.BLACK);
            arr.setBounds(20,x,150,30);
            arr.setVisible(true);
            myPanel.add(arr);

            tarr= new JTextField();
            tarr.setFont(fp.forLabel);
            tarr.setForeground(Color.BLACK);
            tarr.setBounds(175,x,150,30);
            myPanel.add(tarr);
            x+=50;
        // }
        arr2=new JLabel("ShortName");
            arr2.setFont(fp.forLabel);
            arr2.setForeground(Color.BLACK);
            arr2.setBounds(20,x,150,30);
            arr2.setVisible(true);
            myPanel.add(arr2);

            tarr2= new JTextField();
            tarr2.setFont(fp.forLabel);
            tarr2.setForeground(Color.BLACK);
            tarr2.setBounds(175,x,150,30);
            myPanel.add(tarr2);
            x+=50;            
        next = new JButton("<html><u>AddTeam</u></html>");
        next.setFont(fp.forLabel);
        next.setForeground(Color.WHITE);
        next.setBackground(Color.BLACK);
        next.addActionListener(this);
        next.setBounds(120,x,150,30);
        myPanel.add(next);
    }
    private String[][] getTeamsData(int tour_id){
        Conn c = new Conn();
        String teamDataQuery="select team_alias,team_name,team_id from team where tour_id="+tour_id;
        ArrayList<String[]> al = new ArrayList<String[]>();
        try{
            ResultSet rs = c.s.executeQuery(teamDataQuery);
            int serialNo=1;
            while(rs.next()){
                String[] rowData = new String[4];
                rowData[0]=Integer.toString(serialNo++);
                rowData[1]=rs.getString(1);
                rowData[2]=rs.getString(2);
                rowData[3]=Integer.toString(rs.getInt(3));
                al.add(rowData);
            }
        }catch(Exception e){
            System.out.println("in getTeamDataFunction!");
            e.printStackTrace();
        }
        String[][] a= new String[al.size()][4];
        return al.toArray(a);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == back){
            System.out.println("BACK PRESSED");
            
        }else if(ae.getSource() == next){
            //one array for policyDetails
            
            
            try{
                Conn c = new Conn();
                String addTeamQuery = "insert into team(tour_id,team_name,team_alias) values("+tour_id+",'";
                addTeamQuery+=tarr.getText()+"','";
                addTeamQuery+=tarr2.getText()+"')";
                // System.out.println(addTeamQuery);
                c.s.executeUpdate(addTeamQuery);
                this.updateTable();
                tarr.setText("");
                tarr2.setText("");
                // JOptionPane.showMessageDialog(null , notify);
            }catch(Exception e){
                e.printStackTrace();
            }
            // append to add teams
            this.setVisible(false);
        }
    }    
}
