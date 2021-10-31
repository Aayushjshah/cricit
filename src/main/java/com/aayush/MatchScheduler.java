package com.aayush;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.Image;
import javax.swing.JOptionPane;
import java.util.ArrayList;


public class MatchScheduler extends JPanel implements ActionListener{
    public JPanel myPanel = new JPanel();
    public JScrollPane jsp = new JScrollPane(myPanel);
//Addpolicy
    JLabel[] arr = new JLabel[5];
    String[] arrLabel = {"MatchNo.","Team1","Team2","venue","start_date"};
    JScrollPane tablesp;
    JTable jt;
    String[] colName ={"MatchNo.","Match","venue","start_date","match_id"};
     JTextField[] tarr = new JTextField[3];
    JButton back,next;
    JLabel image;
    JComboBox<String> jc1,jc2;
    FontPicker fp = new FontPicker();
    int i;
    int tour_id;
    ArrayList<String> teamNames = new ArrayList<String>();
    ArrayList<Integer> team_ids = new ArrayList<Integer>();
CardLayoutMgr clm;
    
    MatchScheduler(CardLayoutMgr clm,int tour_id){
       //inital db connect to fill up dropdowns
       this.clm=clm;
       this.tour_id=tour_id;
       String tourName="";
       Conn c = new Conn();
       
    //    myPanel.add(jt);
       try{
           //dbConnect
           ResultSet rs = c.s.executeQuery("select name from tournament where tour_id="+tour_id);
           rs.next();
           tourName=rs.getString(1);
           rs=c.s.executeQuery("select team_name,team_id from team where tour_id="+tour_id);
           while(rs.next()){
            teamNames.add(rs.getString(1));
            team_ids.add(rs.getInt(2));
           }
        //    System.out.println(teamNames);
        //    System.out.println(team_ids);
       }catch(Exception e){
           System.out.println("Initial connect AddPolicy");
       }
//panel Settings
        myPanel.setLayout(null);
        myPanel.setBackground(Color.WHITE);
        jsp.setBorder(new EmptyBorder(0,0,0,0));

//subItems
       updateTable();
        JLabel head = new JLabel("<html><u>Tournament : "+tourName+"</u></html>");
        head.setBounds(140,5,600,40);
        head.setFont(fp.headFont);
        head.setForeground(Color.BLACK);
        myPanel.add(head);
        
        JLabel Team = new JLabel("<html><u>Add Match:</u></html>");
        Team.setBounds(40,360,400,30);
        Team.setFont(fp.subHeadFont);
        Team.setForeground(Color.BLACK);
        myPanel.add(Team);

        int x=400;
        arr[0]=new JLabel(arrLabel[0]);
        arr[0].setFont(fp.forLabel);
        arr[0].setForeground(Color.BLACK);
        arr[0].setBounds(20,x,70,30);
        arr[0].setVisible(true);
        myPanel.add(arr[0]);

        tarr[0]= new JTextField();
        tarr[0].setFont(fp.forLabel);
        tarr[0].setForeground(Color.BLACK);
        tarr[0].setBounds(100,x,150,30);
        myPanel.add(tarr[0]);            
        x+=50;
        for(i=1;i<3;i++){
            arr[i]=new JLabel(arrLabel[i]);
            arr[i].setFont(fp.forLabel);
            arr[i].setForeground(Color.BLACK);
            arr[i].setBounds(20,x,70,30);
            arr[i].setVisible(true);
            myPanel.add(arr[i]);
                String[] t = new String[teamNames.size()];
                jc1 = new JComboBox<String>(teamNames.toArray(t));
                jc1.setBounds(100,x,180,30);
                jc1.setBackground(Color.WHITE);
                jc1.addActionListener(this);
                jc1.setFont(fp.forLabel);
                myPanel.add(jc1);
            i++;
            JLabel vs = new JLabel("<html><u>Vs</u></html>");
            vs.setFont(fp.forLabel);
            vs.setForeground(Color.BLACK);
            vs.setBounds(300,x,40,30);
            myPanel.add(vs);

            arr[i]=new JLabel(arrLabel[i]);
            arr[i].setFont(fp.forLabel);
            arr[i].setForeground(Color.BLACK);
            arr[i].setBounds(340,x,70,30);
            arr[i].setVisible(true);
            myPanel.add(arr[i]);

            jc2 = new JComboBox<String>(teamNames.toArray(t));
            jc2.setBounds(400,x,180,30);
            jc2.setBackground(Color.WHITE);
            jc2.addActionListener(this);
            jc2.setFont(fp.forLabel);
            myPanel.add(jc2);           
            x+=50;
        }
        arr[3]=new JLabel(arrLabel[3]);
        arr[3].setFont(fp.forLabel);
        arr[3].setForeground(Color.BLACK);
        arr[3].setBounds(20,x,70,30);
        arr[3].setVisible(true);
        myPanel.add(arr[3]);
        tarr[1]= new JTextField();
        tarr[1].setFont(fp.forLabel);
        tarr[1].setForeground(Color.BLACK);
        tarr[1].setBounds(100,x,180,30);
        myPanel.add(tarr[1]);
        arr[4]=new JLabel(arrLabel[4]);
        arr[4].setFont(fp.forLabel);
        arr[4].setForeground(Color.BLACK);
        arr[4].setBounds(300,x,110,30);
        arr[4].setVisible(true);
        myPanel.add(arr[4]);
        tarr[2]= new JTextField();
        tarr[2].setFont(fp.forLabel);
        tarr[2].setForeground(Color.BLACK);
        tarr[2].setBounds(400,x,180,30);
        myPanel.add(tarr[2]);
        x+=50;
//====        
        next = new JButton("<html><u>Add Match</u></html>");
        next.setFont(fp.forLabel);
        next.setForeground(Color.WHITE);
        next.setBackground(Color.BLACK);
        next.addActionListener(this);
        next.setBounds(220,x+10,150,30);
        myPanel.add(next);
    }


    private void updateTable(){
        jt = new JTable(this.getTeamsData(tour_id),colName);
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
    
    private String[][] getTeamsData(int tour_id){
        Conn c = new Conn();
        String teamDataQuery="select match_type,team1_name,team2_name,venue,start_date,match_id from matches where tour_id="+tour_id+" order by start_date";
        ArrayList<String[]> al = new ArrayList<String[]>();
        try{
            ResultSet rs = c.s.executeQuery(teamDataQuery);
            while(rs.next()){
                String[] rowData = new String[5];
                rowData[0]=rs.getString(1);
                rowData[1]=rs.getString(2)+" Vs "+ rs.getString(3);
                rowData[2]=rs.getString(4);
                rowData[3]=rs.getString(5);
                rowData[4]=Integer.toString(rs.getInt(6));
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
                String MatchSchedulerQuery = "insert into matches(match_type,team1_id,team1_name,team2_id,team2_name,venue,start_date,status,tour_id) values(";
                // MatchSchedulerQuery+=tarr[i]
                // for(int i=0;i<4;i++){
                    MatchSchedulerQuery+="'"+tarr[0].getText()+"',";
                // }
                MatchSchedulerQuery+=team_ids.get((Integer)jc1.getSelectedIndex())+",";
                MatchSchedulerQuery+="'"+jc1.getSelectedItem()+"',";
                MatchSchedulerQuery+=team_ids.get((Integer)jc2.getSelectedIndex())+",";
                MatchSchedulerQuery+="'"+jc2.getSelectedItem()+"',";
                MatchSchedulerQuery+="'"+tarr[1].getText()+"',";
                MatchSchedulerQuery+="'"+tarr[2].getText()+"','unplayed',";
                MatchSchedulerQuery+=tour_id+")";
                
                c.s.executeUpdate(MatchSchedulerQuery);
                
                // System.out.println(MatchSchedulerQuery);
                this.updateTable();
                for(int i=0;i<3;i++){
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
