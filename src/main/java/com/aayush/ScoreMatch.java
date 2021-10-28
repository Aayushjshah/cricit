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
    JComboBox<String> jc1,jc2;
    //team2
    ArrayList<String> playerNamesTeam2 = new ArrayList<String>();
    ArrayList<Integer> player_idsTeam2 = new ArrayList<Integer>();
    JComboBox<String> jc3;

    JButton back,apply;
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
String currBatTeamName,currBatTeamNo="team2";
//======
//Live Scenario
int onStrikeBatId,nonStrikeBatId,currBowlId;
String osBatName,nosBatName,cBowlName;
int ballCount=0,overPerInningCount=0;
int onstrikeBatsmen=0;
//Updation
JButton swap,dotBall,one,two,three,four,six;

//Extras Lot
JButton wide,noBall,byes,updateRuns;
JTextField byeRuns;JLabel byeRunsL;
boolean noBallChecker=false;


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
    //    System.out.println("AAYUSB4DB");
       try{
           //dbConnect
           String query="select team1_id,team2_id,team1_name,team2_name,venue,status,tour_id,start_date,on_strike_batsmen,non_strike_batsmen,on_strike_bat_name,non_strike_bat_name,curr_bowler,curr_bowl_name from matches where match_id="+match_id;
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
            onStrikeBatId=rs.getInt(9);
            nonStrikeBatId=rs.getInt(10);
            osBatName=rs.getString(11);
            nosBatName=rs.getString(12);
            currBowlId=rs.getInt(13);
            cBowlName=rs.getString(14);
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
    //    System.out.println("AAYUSAFtrDB");
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
            currBatTeamNo="team1";
            currentBattingTeamId=team1_id;
        }else{
            currBatTeamName=team2Name;
            currBatTeamNo="team2";
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
        // System.out.println("AAYUSBat");
        // while(onstrikeBatsmen!=2){
        //     //render a new frame to add batsmen.Try doing it in this panel only.
        // }
//match begins
try{
     c = new Conn();
    if(team1_id == currentBattingTeamId){
        String query = "update matches set team1_runs=0,team1_wickets=0,overs_team1=0 where match_id="+match_id;
        c.s.executeUpdate(query);
        // System.out.println(query);
    }else{
        String query = "update matches set team2_runs=0,team2_wickets=0,overs_team2=0 where match_id="+match_id;
        // System.out.println(query);
        c.s.executeUpdate(query);
    }
    String extraQuery = "insert into extras values("+match_id+","+currentBattingTeamId+",0,0,0)";
        c.s.executeUpdate(extraQuery);
}catch(Exception e){
    System.out.println("At matchBegins 240");
    e.printStackTrace();
}


        JLabel BatOptions = new JLabel("<html><u>Player Changes :</u></html>");
            BatOptions.setBounds(40,x,400,30);
            BatOptions.setFont(fp.subHeadFont);
            BatOptions.setForeground(Color.BLACK);
            myPanel.add(BatOptions);
            x+=50;
            JLabel selPlayer=new JLabel("On Strike Batsmen:");
            selPlayer.setFont(fp.forLabel);
            selPlayer.setForeground(Color.BLACK);
            selPlayer.setBounds(20,x,170,30);
            selPlayer.setVisible(true);
            myPanel.add(selPlayer);
            String[] t = new String[playerNamesTeam1.size()];
            jc1 = new JComboBox<String>(playerNamesTeam1.toArray(t));
            jc1.setBounds(200,x,180,30);
            jc1.setBackground(Color.WHITE);
            jc1.addActionListener(this);
            jc1.setFont(fp.forLabel);
            jc1.setSelectedItem("Hardik Pandya");
            myPanel.add(jc1);
            JLabel nonSBat=new JLabel("Non Strike Batsmen:");
            nonSBat.setFont(fp.forLabel);
            nonSBat.setForeground(Color.BLACK);
            nonSBat.setBounds(400,x,180,30);
            nonSBat.setVisible(true);
            myPanel.add(nonSBat);
                t = new String[playerNamesTeam1.size()];
                jc2 = new JComboBox<String>(playerNamesTeam1.toArray(t));
                jc2.setBounds(580,x,180,30);
                jc2.setBackground(Color.WHITE);
                jc2.addActionListener(this);
                jc2.setFont(fp.forLabel);
                jc2.setSelectedItem("Hardik Pandya");
                // jc2.setEnabled(false);
                myPanel.add(jc2);
                x+=30;
            // bolwing
            JLabel selBowl=new JLabel("Select Bowler");
            selBowl.setFont(fp.forLabel);
            selBowl.setForeground(Color.BLACK);
            selBowl.setBounds(20,x,170,30);
            selBowl.setVisible(true);

            myPanel.add(selBowl);
                t = new String[playerNamesTeam2.size()];
            jc3 = new JComboBox<String>(playerNamesTeam2.toArray(t));
            jc3.setBounds(200,x,180,30);
            jc3.setBackground(Color.WHITE);
            jc3.addActionListener(this);
            jc3.setFont(fp.forLabel);
            jc2.setSelectedItem("Dhoni");
            myPanel.add(jc3);

            apply = new JButton("<html><u>Apply</u></html>");
            apply.setFont(fp.forLabel);
            apply.setForeground(Color.WHITE);
            apply.setBackground(Color.BLACK);
            apply.addActionListener(this);
            apply.setBounds(480,x+5,180,30);
            myPanel.add(apply);
            x+=40;
    // ===Batting
JLabel BatOp = new JLabel("<html><u>Player Changes :</u></html>");
                BatOp.setBounds(40,x,400,30);
                BatOp.setFont(fp.subHeadFont);
                BatOp.setForeground(Color.BLACK);
                myPanel.add(BatOp);
                x+=50;
//now options begin.
/*
At the start of the match we wish to set overs as 0
*/

// swap button
// JButton swap,dotBall,one,two,three,four,six;
swap = new JButton("<html><u>swapStrike</u></html>");
    swap.setFont(fp.forLabel);
    swap.setForeground(Color.WHITE);
    swap.setBackground(Color.BLACK);
    swap.addActionListener(this);
    swap.setBounds(0,x,120,30);
    myPanel.add(swap);
    x+=40;
    JLabel runsScored = new JLabel("<html><u>Runs Scored:</u></html>");
    runsScored.setBounds(0,x,100,30);
    runsScored.setFont(fp.forLabel);
    runsScored.setForeground(Color.BLACK);
    myPanel.add(runsScored);
    // x+=50;

    int y=110;
    dotBall = new JButton("<html><u>dotBall</u></html>");
    dotBall.setFont(fp.forLabel);
    dotBall.setForeground(Color.WHITE);
    dotBall.setBackground(Color.BLACK);
    dotBall.addActionListener(this);
    dotBall.setBounds(y,x,100,30);
    myPanel.add(dotBall);
    y+=120;
    one = new JButton("<html><u>1</u></html>");
    one.setFont(fp.forLabel);
    one.setForeground(Color.WHITE);
    one.setBackground(Color.BLACK);
    one.addActionListener(this);
    one.setBounds(y,x,60,30);
    myPanel.add(one);
    y+=70;
    two = new JButton("<html><u>2</u></html>");
    two.setFont(fp.forLabel);
    two.setForeground(Color.WHITE);
    two.setBackground(Color.BLACK);
    two.addActionListener(this);
    two.setBounds(y,x,60,30);
    myPanel.add(two);
    y+=70;
    three = new JButton("<html><u>3</u></html>");
    three.setFont(fp.forLabel);
    three.setForeground(Color.WHITE);
    three.setBackground(Color.BLACK);
    three.addActionListener(this);
    three.setBounds(y,x,60,30);
    myPanel.add(three);
    y+=70;
    four = new JButton("<html><u>4</u></html>");
    four.setFont(fp.forLabel);
    four.setForeground(Color.WHITE);
    four.setBackground(Color.BLACK);
    four.addActionListener(this);
    four.setBounds(y,x,60,30);
    myPanel.add(four);
    y+=70;
    six = new JButton("<html><u>6</u></html>");
    six.setFont(fp.forLabel);
    six.setForeground(Color.WHITE);
    six.setBackground(Color.BLACK);
    six.addActionListener(this);
    six.setBounds(y,x,60,30);
    myPanel.add(six);
            x+=40;
// JButton wide,noBall,lb,byes,updateRuns;
// JTextField byeRuns;JLabel byeRunsL;
JLabel Extras = new JLabel("<html><u>Extras:</u></html>");
    Extras.setBounds(0,x,100,30);
    Extras.setFont(fp.forLabel);
    Extras.setForeground(Color.BLACK);
    myPanel.add(Extras);
    // x+=50;

    y=110;
    wide = new JButton("<html><u>wide</u></html>");
    wide.setFont(fp.forLabel);
    wide.setForeground(Color.WHITE);
    wide.setBackground(Color.BLACK);
    wide.addActionListener(this);
    wide.setBounds(y,x,100,30);
    myPanel.add(wide);
    y+=120;
    noBall = new JButton("<html><u>nb</u></html>");
    noBall.setFont(fp.forLabel);
    noBall.setForeground(Color.WHITE);
    noBall.setBackground(Color.BLACK);
    noBall.addActionListener(this);
    noBall.setBounds(y,x,60,30);
    myPanel.add(noBall);
    y+=70;
    byes = new JButton("<html><u>byes</u></html>");
    byes.setFont(fp.forLabel);
    byes.setForeground(Color.WHITE);
    byes.setBackground(Color.BLACK);
    byes.addActionListener(this);
    byes.setBounds(y,x,60,30);
    myPanel.add(byes);
    y+=100;
    byeRunsL = new JLabel("<html><u>byeRuns:</u></html>");
    byeRunsL.setBounds(y,x,80,30);
    byeRunsL.setFont(fp.forLabel);
    byeRunsL.setForeground(Color.BLACK);
    byeRunsL.setVisible(false);
    myPanel.add(byeRunsL);
    y+=80;
    byeRuns = new JTextField();
    byeRuns.setFont(fp.forLabel);
    // y+=70;
    // byeRuns.setForeground(Color.WHITE);
    // byeRuns.setBackground(Color.BLACK);
    byeRuns.addActionListener(this);
    byeRuns.setBounds(y,x,60,30);
    byeRuns.setVisible(false);
    myPanel.add(byeRuns);
    y+=90;
    updateRuns = new JButton("<html><u>updateRuns</u></html>");
    updateRuns.setFont(fp.forLabel);
    updateRuns.setForeground(Color.WHITE);
    updateRuns.setBackground(Color.GREEN);
    updateRuns.addActionListener(this);
    updateRuns.setBounds(y,x,120,30);
    updateRuns.setVisible(false);
    myPanel.add(updateRuns);
    x+=40;
// ==WICKETS====
JButton out,runOut,retdHurt,changeBowler;
// JTextField byeRuns;
JLabel Wickets = new JLabel("<html><u>Wickets:</u></html>");
    Wickets.setBounds(0,x,100,30);
    Wickets.setFont(fp.forLabel);
    Wickets.setForeground(Color.BLACK);
    myPanel.add(Wickets);
    // x+=50;

    y=110;
    out = new JButton("<html><u>Out</u></html>");
    out.setFont(fp.forLabel);
    out.setForeground(Color.WHITE);
    out.setBackground(Color.BLACK);
    out.addActionListener(this);
    out.setBounds(y,x,100,30);
    myPanel.add(out);
    y+=110;
    runOut = new JButton("<html><u>Run out</u></html>");
    runOut.setFont(fp.forLabel);
    runOut.setForeground(Color.WHITE);
    runOut.setBackground(Color.BLACK);
    runOut.addActionListener(this);
    runOut.setBounds(y,x,100,30);
    myPanel.add(runOut);
    y+=110;
    retdHurt = new JButton("<html><u>retdHurt</u></html>");
    retdHurt.setFont(fp.forLabel);
    retdHurt.setForeground(Color.WHITE);
    retdHurt.setBackground(Color.BLACK);
    retdHurt.addActionListener(this);
    retdHurt.setBounds(y,x,100,30);
    myPanel.add(retdHurt);
    y+=170;
    changeBowler = new JButton("<html><u>change Bowler</u></html>");
    changeBowler.setFont(fp.forLabel);
    changeBowler.setForeground(Color.WHITE);
    changeBowler.setBackground(Color.BLACK);
    changeBowler.addActionListener(this);
    changeBowler.setBounds(y,x,180,30);
    myPanel.add(changeBowler);
    x+=60;
    // y+=170;
JButton endInnings,endMatch;
    endInnings = new JButton("<html><u>end Innings</u></html>");
    endInnings.setFont(fp.forLabel);
    endInnings.setForeground(Color.WHITE);
    endInnings.setBackground(Color.BLACK);
    endInnings.addActionListener(this);
    endInnings.setBounds(0,x,180,30);
    myPanel.add(endInnings);

    y+=170;
    endMatch = new JButton("<html><u>end Match</u></html>");
    endMatch.setFont(fp.forLabel);
    endMatch.setForeground(Color.WHITE);
    endMatch.setBackground(Color.BLACK);
    endMatch.addActionListener(this);
    endMatch.setBounds(200,x,180,30);
    myPanel.add(endMatch);
//Front End ENd
// init overs,batting team runs DONE
// int ballCount=0,overPerInningCount=0;
// int onstrikeBatsmen=0;

//=============================
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==apply){
            onStrikeBatId=player_idsTeam1.get((Integer)jc1.getSelectedIndex());
            nonStrikeBatId=player_idsTeam1.get((Integer)jc2.getSelectedIndex());
            currBowlId=player_idsTeam2.get((Integer)jc3.getSelectedIndex());
            osBatName=(String)jc1.getSelectedItem();
            nosBatName=(String)jc2.getSelectedItem();
            cBowlName=(String)jc3.getSelectedItem();
            System.out.println(onStrikeBatId+":"+nonStrikeBatId+":"+currBowlId);
            String query2,query;
            try{
                Conn c = new Conn();
                
            if(jc1.isEnabled()){
                //init in scorecard batting
                query2="update matches set on_strike_batsmen="+onStrikeBatId +
                ", on_strike_bat_name='"+osBatName+
                "' where match_id="+match_id;
                c.s.executeUpdate(query2);
                query = "insert into scorecardBatting values("+match_id+","+onStrikeBatId+","
                            +"0,0,0,0,'not_out',null)";
                c.s.executeUpdate(query);
                // System.out.println(query);
            }if(jc2.isEnabled()){
                //init in scorecard batting
                query2="update matches set non_strike_batsmen="+nonStrikeBatId +
                ", non_strike_bat_name='"+nosBatName+
                "' where match_id="+match_id;
                c.s.executeUpdate(query2);
                query = "insert into scorecardBatting values("+match_id+","+nonStrikeBatId+","
                            +"0,0,0,0,'not_out',null)";
                // System.out.println(query);
                c.s.executeUpdate(query);
            }if(jc3.isEnabled()){
                //init in scorecard batting
                query2="update matches set curr_bowler="+currBowlId+
                ", curr_bowl_name='"+cBowlName+"' where match_id="+match_id;
                c.s.executeUpdate(query2);
                query = "insert into scorecardBowling values("+match_id+","+currBowlId+","
                            +"0,0,0,0)";
                // System.out.println(query);
                c.s.executeUpdate(query);
            }
            }catch(Exception e){
                System.out.println("In apply try block");
                e.printStackTrace();
            }
            apply.setEnabled(false);
            jc1.setEnabled(false);
            jc2.setEnabled(false);
            jc3.setEnabled(false);
        }
//apply ends
//scoreUpdation
        if(currentBattingTeamId==team2_id){   
            Conn c=new Conn();
            try{
                if(ae.getSource()==dotBall){
                    //dotball
                    //add to scorecardbatting,bowling ,runs of the team   
                    this.ballUpdaterTeam2();
                }else if(ae.getSource()==one){
                    //dotball
                    //add to scorecardbatting,bowling ,runs of the team
                    this.ballUpdaterTeam2();
                    //add runs
                    this.addRuns123(1);
                }else if(ae.getSource()==two){
                    //dotball
                    //add to scorecardbatting,bowling ,runs of the team
                    this.ballUpdaterTeam2();
                    //add runs
                    this.addRuns123(2);
                }else if(ae.getSource()==three){
                    //dotball
                    //add to scorecardbatting,bowling ,runs of the team
                    this.ballUpdaterTeam2();
                    //add runs
                    this.addRuns123(3);
                }else if(ae.getSource()==four){
                    //dotball
                    //add to scorecardbatting,bowling ,runs of the team
                    this.ballUpdaterTeam2();
                    //add runs
                    this.addRuns123(4);
                    //scoreCard
                    String batScoreQuery="update scorecardBatting set fours=fours+1 where "+
                        "match_id="+match_id+" and player_id="+onStrikeBatId;
                    System.out.println(batScoreQuery);
                    c.s.executeUpdate(batScoreQuery);
                    //update runs in bowler
                }else if(ae.getSource()==six){
                    //dotball
                    //add to scorecardbatting,bowling ,runs of the team
                    this.ballUpdaterTeam2();
                    //add runs
                    this.addRuns123(6);
                    //scoreCard
                    String batScoreQuery="update scorecardBatting set sixes=sixes+1 where "+
                        "match_id="+match_id+" and player_id="+onStrikeBatId;
                    System.out.println(batScoreQuery);
                    c.s.executeUpdate(batScoreQuery);
                    //update runs in bowler
                }
                //runs normal ends
                //extras
                else if(ae.getSource()==wide){
                    //updating extras;
                    String extraQuery="update extras set wide=wide+"+1+" where match_id="+match_id+" and bat_team_id="+currentBattingTeamId;
                    System.out.println(extraQuery);
                    c.s.executeUpdate(extraQuery);
                    // callByes.
                    byeRunsL.setVisible(true);
                    byeRuns.setVisible(true);
                    updateRuns.setVisible(true);
                    
                }else if(ae.getSource()==byes){
                    byeRunsL.setVisible(true);
                    byeRuns.setVisible(true);
                    updateRuns.setVisible(true);
                    try{
                        int b =Integer.parseInt(byeRuns.getText());
                        this.addByes(b);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else if(ae.getSource()==noBall){
                    //updating extras;
                    noBallChecker=true;
                    String extraQuery="update extras set no_ball=no_ball+"+1+" where match_id="+match_id+" and bat_team_id="+currentBattingTeamId;
                    System.out.println(extraQuery);
                    c.s.executeUpdate(extraQuery);
                    // callByes.
                    byeRunsL.setVisible(true);
                    byeRuns.setVisible(true);
                    updateRuns.setVisible(true);
                }else if(ae.getSource()==updateRuns){
                    try{
                        int b =Integer.parseInt(byeRuns.getText());
                        if(!noBallChecker){
                            this.addByes(b);
                        }else{
                            //No_ballRuns
                            this.addRuns123(b);
                            if(b==6){
                                String batScoreQuery="update scorecardBatting set sixes=sixes+1 where "+
                                    "match_id="+match_id+" and player_id="+onStrikeBatId;
                                System.out.println(batScoreQuery);
                                c.s.executeUpdate(batScoreQuery);
                            }else if(b==4){
                                String batScoreQuery="update scorecardBatting set fours=fours+1 where "+
                                    "match_id="+match_id+" and player_id="+onStrikeBatId;
                                System.out.println(batScoreQuery);
                                c.s.executeUpdate(batScoreQuery);
                            }
                        }
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    byeRunsL.setVisible(false);
                    byeRuns.setVisible(false);
                    updateRuns.setVisible(false);
                }
            }catch(Exception e){
                System.out.println("addAction 632");
            e.printStackTrace();
            }
        }
    }
    private void addByes(int r){
        Conn c= new Conn();
        try{
            String updateTeamScore="update matches set team2_runs=team2_runs+"+r+
                " where match_id="+match_id;
            System.out.println(updateTeamScore);
        //add Runs to  byes and teamScore Only.
            String extraQuery="update extras set byes=byes+"+r+" where match_id="+match_id+" and bat_team_id="+currentBattingTeamId;
                System.out.println(extraQuery);
             c.s.executeUpdate(extraQuery);
        }catch(Exception e){
            System.out.println("addByes");
            e.printStackTrace();
        }  
    }
    private void addRunsExtrasTeam2(int r){
        //matchScore
        Conn c= new Conn();
        try{
            String updateTeamScore="update matches set team2_runs=team2_runs+"+r+
                " where match_id="+match_id;
            System.out.println(updateTeamScore);
            // c.s.executeUpdate(updateTeamScore);
            //update runs inBowlerScoreCard
            String bowlScoreQuery="update scorecardBowling set runs_given=runs_given+"+r+" where "+
            "match_id="+match_id+" and player_id="+currBowlId;
            System.out.println(bowlScoreQuery);
            // c.s.executeUpdate(bowlScoreQuery);
            //add Runs to byes
        }catch(Exception e){
            System.out.println("addRunsExtras");
            e.printStackTrace();
        }  
    }
    private void addRuns123(int r){
    //scoreCard
        Conn c= new Conn();
        try{
            String batScoreQuery="update scorecardBatting set runs=runs+"+r+" where "+
                "match_id="+match_id+" and player_id="+onStrikeBatId;
            System.out.println(batScoreQuery);
            // c.s.executeUpdate(batScoreQuery);
    //matchScore
            String updateTeamScore="update matches set team2_runs=team2_runs+"+r+
                    " where match_id="+match_id;
            System.out.println(updateTeamScore);
            // c.s.executeUpdate(updateTeamScore);
    //update runs inBowlerScoreCard
            String bowlScoreQuery="update scorecardBowling set runs_given=runs_given+"+r+" where "+
            "match_id="+match_id+" and player_id="+currBowlId;
            System.out.println(bowlScoreQuery);
            c.s.executeUpdate(bowlScoreQuery);
            
        }catch(Exception e){
            System.out.println("addRuns123");
            e.printStackTrace();
        }  
    }
    private void ballUpdaterTeam2(){
        //scoreCard Bowling,batting ,overs in matches
        Conn c = new Conn();
        try{
            ballCount++;
            String batScoreQuery="update scorecardBatting set balls_played=balls_played+1 where "+
            "match_id="+match_id+" and player_id="+onStrikeBatId;
            System.out.println(batScoreQuery);
            // c.s.executeUpdate(batScoreQuery);
            if(ballCount!=6){
                String updateOvers="update matches set overs_team2=overs_team2+"+(ballCount*0.1)
                +" where match_id="+match_id;
                System.out.println(updateOvers);
                // c.s.executeUpdate(updateOvers);

                //scorecardBOwling
                updateOvers="update scorecardBowling set overs=overs+"+(ballCount*0.1)
                +" where match_id="+match_id+" and player_id="+currBowlId;
                System.out.println(updateOvers);
                // c.s.executeUpdate(updateOvers);
            }else{
                String updateOvers="update matches set overs_team2=overs_team2+1"
                +" where match_id="+match_id;
                System.out.println(updateOvers);
                // c.s.executeUpdate(batScoreQuery);

                //scorecardBOwling
                updateOvers="update scorecardBowling set overs=overs+"+(ballCount*0.1)
                +" where match_id="+match_id+" and player_id="+currBowlId;
                System.out.println(updateOvers);
                // c.s.executeUpdate(updateOvers);
            }
        }catch(Exception e){
            System.out.println("ballUpdaterTeam2");
            e.printStackTrace();
        }
    }
}
