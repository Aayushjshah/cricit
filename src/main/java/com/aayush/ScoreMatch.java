package com.aayush;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;
import javax.swing.ImageIcon;
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


public class ScoreMatch extends JPanel implements ActionListener{
    public JPanel myPanel = new JPanel();
    public JScrollPane jsp = new JScrollPane(myPanel);
//store players  
    //team1
    ArrayList<String> playerNamesTeam1 = new ArrayList<String>();
    ArrayList<Integer> player_idsTeam1 = new ArrayList<Integer>();
    JComboBox<String> jc1;
    //team2
    ArrayList<String> playerNamesTeam2 = new ArrayList<String>();
    ArrayList<Integer> player_idsTeam2 = new ArrayList<Integer>();
    JComboBox<String> jc2;

    JButton back,next;
    JLabel image;
    FontPicker fp = new FontPicker();
    int i;
    int match_id;
    int bat_first_id;
    String team1Name,team1NameShort,team2Name,team2NameShort;
    int team1_id,team2_id;
    int toss_won_id;String tossWinName;
//===
// MatchDetails
String venue,status,date;
int currentBattingTeamId =bat_first_id;
int oversPerSide,tour_id;
String currBatTeamName;
CardLayoutMgr clm;
    ScoreMatch(CardLayoutMgr clm,int match_id,int bat_first_id,int toss_won_id){
        //panel Settings
        myPanel.setLayout(null);
        myPanel.setBackground(Color.WHITE);
        jsp.setBorder(new EmptyBorder(0,0,0,0));
     //variable fillup  
       this.clm=clm;
       this.match_id=match_id;
       this.bat_first_id=bat_first_id;
       this.toss_won_id=toss_won_id;
       Conn c = new Conn();
       try{
           //dbConnect
           String query="select team1_id,team2_id,team1_name,team2_name,venue,status,tour_id,start_date from matches where match_id="+match_id;
            ResultSet rs = c.s.executeQuery(query);
            rs.next();
            team1_id=rs.getInt(1);
            team2_id=rs.getInt(2);
            team1Name=rs.getString(3);
            team2Name=rs.getString(4);
            venue=rs.getString(5);
            status=rs.getString(6);
            tour_id=rs.getInt(7);
            date=rs.getString(8);
        //for team2 short
            query="select team_alias from team where team_id="+team1_id;
            rs=c.s.executeQuery(query);
            rs.next();
            team1NameShort=rs.getString(1);
        //for team2 short
            query="select team_alias from team where team_id="+team2_id;
            rs=c.s.executeQuery(query);
            rs.next();
            team2NameShort=rs.getString(1);
        //oversperside
            query="select overs from tournament where tour_id="+tour_id;
            rs=c.s.executeQuery(query);
            rs.next();
            oversPerSide=rs.getInt(1);
        //append playersList1
            rs=c.s.executeQuery("select name,player_id from players where team_id="+team1_id);
            while(rs.next()){
                playerNamesTeam1.add(rs.getString(1));
                player_idsTeam1.add(rs.getInt(2));
            }
        //append playersList1
            rs=c.s.executeQuery("select name,player_id from players where team_id="+team2_id);
            while(rs.next()){
                playerNamesTeam2.add(rs.getString(1));
                player_idsTeam2.add(rs.getInt(2));
            }
       }catch(Exception e){
           System.out.println("Initial connect AddPolicy");
       }
       //tosswinName DOne
       if(toss_won_id==team1_id){
           tossWinName=team1Name;
       }else{
           tossWinName=team2Name;
       }
//creating tossString
        String tossString=tossWinName+" won the toss and choose to ";
        if(bat_first_id == toss_won_id){
            tossString+="bat.";
        }else{
            tossString+="bowl.";
        }
        if(bat_first_id == team1_id){
            currBatTeamName=team1Name;
            currentBattingTeamId=team1_id;
        }else{
            currBatTeamName=team2Name;
            currentBattingTeamId=team2_id;
        }
//Creating versusString
        String vsString=team1Name+"("+team1_id+") Vs "+team2Name+"("+team2_id+").";
//subItems
        String headStr =team1NameShort + " Vs "+team2NameShort;
        JLabel head = new JLabel("<html><u>"+headStr+"</u></html>");// teamALias heading
        head.setBounds(240,5,400,30);
        head.setFont(fp.subHeadFont);
        head.setForeground(Color.BLACK);
        myPanel.add(head);

        JLabel Tournament = new JLabel("<html><u>Match Details:</u></html>");
        Tournament.setBounds(40,40,400,30);
        Tournament.setFont(fp.subHeadFont);
        Tournament.setForeground(Color.BLACK);
        myPanel.add(Tournament);
//oversPerSide:from tourId.
//team1Name(team1_id) vs //team2Name(team2_id)
//venue;//status:ongoing
//currentBattingTeamId:
//date:
//tossDetails :tossWonBY: choice: ____ won the toss and choose to ___
// ===
//scoreCard like in DOc.
//====================================================================
            int x=80;
            JLabel arr=new JLabel("<html><u>"+vsString+"</u></html>");
            arr.setFont(fp.forLabel);
            arr.setForeground(Color.BLACK);
            arr.setBounds(20,x,500,30);
            arr.setVisible(true);
            myPanel.add(arr);

            JLabel arr2=new JLabel("Date:"+date);
            arr2.setFont(fp.forLabel);
            arr2.setForeground(Color.BLACK);
            arr2.setBounds(510,x,150,30);
            arr2.setVisible(true);
            myPanel.add(arr2);
            x+=30;
            JLabel arr3=new JLabel("<html><u>"+tossString+"</u></html>");;
            arr3.setFont(fp.forLabel);
            arr3.setForeground(Color.BLACK);
            arr3.setBounds(20,x,500,30);
            arr3.setVisible(true);
            myPanel.add(arr3);

            JLabel arr4=new JLabel("Venue:"+venue);
            arr4.setFont(fp.forLabel);
            arr4.setForeground(Color.BLACK);
            arr4.setBounds(510,x,150,30);
            arr4.setVisible(true);
            myPanel.add(arr4);
            x+=30;
            JLabel arr5=new JLabel("currBatTeamName:"+currBatTeamName+"("+currentBattingTeamId+")");
            arr5.setFont(fp.forLabel);
            arr5.setForeground(Color.BLACK);
            arr5.setBounds(20,x,400,30);
            arr5.setVisible(true);
            myPanel.add(arr5);

            JLabel arr6=new JLabel("Status:"+status);
            arr6.setFont(fp.forLabel);
            arr6.setForeground(Color.BLACK);
            arr6.setBounds(430,x,150,30);
            arr6.setVisible(true);
            myPanel.add(arr6);

            JLabel arr7=new JLabel("oversPerSide:"+oversPerSide);
            arr7.setFont(fp.forLabel);
            arr7.setForeground(Color.BLACK);
            arr7.setBounds(590,x,150,30);
            arr7.setVisible(true);
            myPanel.add(arr7);
            x+=50;
//===============================
// Batting Updations:
            int onstrikeBatsmen=0;
            while(onstrikeBatsmen!=2){
                //render a new frame to add batsmen.Try doing it in this panel only.
            }
            JLabel BatOptions = new JLabel("<html><u>Batting :</u></html>");
                BatOptions.setBounds(40,x,400,30);
                BatOptions.setFont(fp.subHeadFont);
                BatOptions.setForeground(Color.BLACK);
                myPanel.add(BatOptions);
                x+=50;
               JLabel selPlayer=new JLabel("Select Player");
                selPlayer.setFont(fp.forLabel);
                selPlayer.setForeground(Color.BLACK);
                selPlayer.setBounds(20,x,70,30);
                selPlayer.setVisible(true);
                myPanel.add(selPlayer);
                    String[] t = new String[playerNamesTeam1.size()];
                    jc1 = new JComboBox<String>(playerNamesTeam1.toArray(t));
                    jc1.setBounds(100,x,180,30);
                    jc1.setBackground(Color.WHITE);
                    jc1.addActionListener(this);
                    jc1.setFont(fp.forLabel);
                    myPanel.add(jc1);
                    // bolwing
            // t = new String[playerNamesTeam2.size()];
            // jc2 = new JComboBox<String>(playerNamesTeam2.toArray(t));
            // jc2.setBounds(400,x,180,30);
            // jc2.setBackground(Color.WHITE);
            // jc2.addActionListener(this);
            // jc2.setFont(fp.forLabel);
            // myPanel.add(jc2);           


//=============================
            // tarr= new JTextField();
            // tarr.setFont(fp.forLabel);
            // tarr.setForeground(Color.BLACK);
            // tarr.setBounds(175,x,150,30);
            // myPanel.add(tarr);
            // x+=50;
        // }

        // next = new JButton("<html><u>ScoreMatch</u></html>");
        // next.setFont(fp.forLabel);
        // next.setForeground(Color.WHITE);
        // next.setBackground(Color.BLACK);
        // next.addActionListener(this);
        // next.setBounds(120,x,150,30);
        // myPanel.add(next);
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
    }
}
