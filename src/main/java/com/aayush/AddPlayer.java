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


public class AddPlayer extends JPanel implements ActionListener{
    public JPanel myPanel = new JPanel();
    public JScrollPane jsp = new JScrollPane(myPanel);
//Addpolicy
    JLabel[] arr = new JLabel[5];
    String[] arrLabel = {"Name","Skill","Hand","Role","Age"};
    JScrollPane tablesp;
    JTable jt;
    String[] colName={"SrNo.","Name","Skill","Hand","Role","Age","player_id"};
     JTextField[] tarr = new JTextField[5];
    JButton back,next;
    JLabel image;
    FontPicker fp = new FontPicker();
    int i;
    int team_id;
CardLayoutMgr clm;
    
    AddPlayer(CardLayoutMgr clm,int team_id){
       //inital db connect to fill up dropdowns
       this.clm=clm;
       this.team_id=team_id;
       String teamName="";
       Conn c = new Conn();
       
    //    myPanel.add(jt);
       try{
           //dbConnect
           //forTourConnect
           ResultSet rs = c.s.executeQuery("select team_name from team where team_id="+team_id);
           rs.next();
            teamName=rs.getString(1);
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
        JLabel head = new JLabel("<html><u>Team : "+teamName+"</u></html>");
        head.setBounds(240,5,400,40);
        head.setFont(fp.headFont);
        head.setForeground(Color.BLACK);
        myPanel.add(head);
        
        JLabel Team = new JLabel("<html><u>Add Player:</u></html>");
        Team.setBounds(40,360,400,30);
        Team.setFont(fp.subHeadFont);
        Team.setForeground(Color.BLACK);
        myPanel.add(Team);

        int x=400;
        for(i=0;i<4;i++){
            arr[i]=new JLabel(arrLabel[i]);
            arr[i].setFont(fp.forLabel);
            arr[i].setForeground(Color.BLACK);
            arr[i].setBounds(20,x,70,30);
            arr[i].setVisible(true);
            myPanel.add(arr[i]);

            tarr[i]= new JTextField();
            tarr[i].setFont(fp.forLabel);
            tarr[i].setForeground(Color.BLACK);
            tarr[i].setBounds(100,x,150,30);
            myPanel.add(tarr[i]);
            i++;
            arr[i]=new JLabel(arrLabel[i]);
            arr[i].setFont(fp.forLabel);
            arr[i].setForeground(Color.BLACK);
            arr[i].setBounds(320,x,70,30);
            arr[i].setVisible(true);
            myPanel.add(arr[i]);

            tarr[i]= new JTextField();
            tarr[i].setFont(fp.forLabel);
            tarr[i].setForeground(Color.BLACK);
            tarr[i].setBounds(400,x,150,30);
            myPanel.add(tarr[i]);            
            x+=50;
        }
        arr[i]=new JLabel(arrLabel[i]);
        arr[i].setFont(fp.forLabel);
        arr[i].setForeground(Color.BLACK);
        arr[i].setBounds(20,x,70,30);
        arr[i].setVisible(true);
        myPanel.add(arr[i]);

        tarr[i]= new JTextField();
        tarr[i].setFont(fp.forLabel);
        tarr[i].setForeground(Color.BLACK);
        tarr[i].setBounds(100,x,150,30);
        myPanel.add(tarr[i]);            
        x+=50;
        next = new JButton("<html><u>AddPlayer</u></html>");
        next.setFont(fp.forLabel);
        next.setForeground(Color.WHITE);
        next.setBackground(Color.BLACK);
        next.addActionListener(this);
        next.setBounds(220,x+10,150,30);
        myPanel.add(next);
    }


    private void updateTable(){
        jt = new JTable(this.getTeamsData(team_id),colName);
        // jt = new JTable(colName,colName);
        JTableHeader header = jt.getTableHeader();
        header.setFont(fp.subHeadFont);
        header.setBackground(new Color(230, 255, 230));
        tablesp = new JScrollPane(jt);
        // tablesp.setBackground(Color);
        myPanel.add(tablesp);
        tablesp.setBounds(10,80,780,270);
       jt.setRowHeight(30);
       jt.setFont(fp.forLabel);
       tablesp.setBorder(new EmptyBorder(0,0,0,0));
       jt.setBackground(Color.GREEN);
    }
    
    private String[][] getTeamsData(int team_id){
        Conn c = new Conn();
        String teamDataQuery="select name,skill,hand,role,age,player_id from players where team_id="+team_id;
        ArrayList<String[]> al = new ArrayList<String[]>();
        try{
            ResultSet rs = c.s.executeQuery(teamDataQuery);
            int serialNo=1;
            while(rs.next()){
                String[] rowData = new String[7];
                rowData[0]=Integer.toString(serialNo++);
                rowData[1]=rs.getString(1);
                rowData[2]=rs.getString(2);
                rowData[3]=rs.getString(3);
                rowData[4]=rs.getString(4);
                rowData[5]=Integer.toString(rs.getInt(5));
                rowData[6]=Integer.toString(rs.getInt(6));
                al.add(rowData);
            }
        }catch(Exception e){
            System.out.println("in getTeamDataFunction!");
            e.printStackTrace();
        }
        String[][] a= new String[al.size()][7];
        return al.toArray(a);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == back){
            System.out.println("BACK PRESSED");
            
        }else if(ae.getSource() == next){
            //one array for policyDetails
            try{
                Conn c = new Conn();
                String AddPlayerQuery = "insert into players(name,skill,hand,role,age,team_id) values(";
                // AddPlayerQuery+=tarr[i]
                for(int i=0;i<4;i++){
                    AddPlayerQuery+="'"+tarr[i].getText()+"',";
                }
                AddPlayerQuery+=tarr[i].getText()+",";
                AddPlayerQuery+=team_id+")";
                
                c.s.executeUpdate(AddPlayerQuery);
                ResultSet rs = c.s.executeQuery("select last_insert_id()");
                rs.next();
                // int player_id=rs.getInt(1);
                String statQuery = "insert into stats values("+rs.getInt(1)+",0,0,0,0,0,0,0,0,0,0,0,0,0)";
                c.s.executeUpdate(statQuery);
                // System.out.println(statQuery);
                this.updateTable();
                for(int i=0;i<5;i++){
                    tarr[i].setText("");;
                }
                
            }catch(Exception e){
                e.printStackTrace();
            }
            // append to add teams
            this.setVisible(false);
        }
    }    
}
