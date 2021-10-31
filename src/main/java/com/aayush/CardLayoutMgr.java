package com.aayush;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
// import javax.swing.JButton;
import java.awt.Color;
// import java.awt.event.ActionListener;


public class CardLayoutMgr extends JFrame{
    CardLayout cl = new CardLayout();
    
        
    int i=0;
    final int height=650;
    final int fwidth = 900;
    final int pclwidth= 660;
    final int pclheight=605;
    FontPicker fp = new FontPicker();
    JPanel pcl = new JPanel(cl);
    VerticalNav vn = new VerticalNav(this);
    JPanel p1 = vn.p1;
    CardLayoutMgr(String username){
        
        setBounds(350,120,fwidth,height);
        pcl.setBounds(220,5,pclwidth,pclheight);   //the outer right panel
        pcl.setBackground(Color.WHITE);
        // pcl.setLayout(cl);
        add(p1);
        CreateTournament ct = new CreateTournament(this, username);
        AddTeam at= new AddTeam(this,8,4);
        // ScoreMatch(CardLayoutMgr clm,int match_id,int bat_first_id,int toss_won_id){
        // System.out.println("AAYUS");
        ScoreMatch sm = new ScoreMatch(this,12,8,9);
        // System.out.println("AAYUS");
        AddPlayer ap = new AddPlayer(this,9);
        MatchScheduler ms = new MatchScheduler(this,8);
        
        pcl.add(ct.jsp,"ct");
        pcl.add(at.jsp,"at");
        pcl.add(ap.jsp,"ap");
        pcl.add(ms.jsp,"ms");
        pcl.add(sm.jsp,"sm");
        add(pcl);
        // cl.show(pcl,"ct");
        // this.varSize(155,0);
        cl.show(pcl,"sm");
        this.varSize(155,0);
        // cl.show(pcl,"ct");
        

//end
        setLayout(null);
        // this.setUndecorated(true);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setr(String i){
        pcl.setSize(pclwidth,pclheight);
            this.setSize(fwidth,height);
        if(i.equals("ipdd1")){
            this.varSize(0,0);
        }else if(i.equals("dscl")){
            this.varSize(100,0);
        }else if(i.equals("apm2") || i.equals("apm")){
            this.varSize(155,0);
        }else if(i.equals("upd") || i.equals("upd2")){
            this.varSize(155,0);
        }else if(i.equals("upm") || i.equals("upm2")){
            this.varSize(0,0);
        }
        else{
        }
        cl.show(pcl,i);     
    }
    // public void adderPdd2(PolicyDetailsDisplay2 c){
    //     pcl.add(c.jsp,"pdd2");
    // }
    public void varSize(int widthInc,int heightInc){
        pcl.setSize(pcl.getWidth()+ widthInc, pcl.getHeight()+heightInc);
        this.setSize(this.getWidth()+widthInc,this.getHeight()+heightInc);
    }
    public void setScoreMatch(){
        cl.show(pcl,"sm");
    }

    public static void main(String[] args){
        new CardLayoutMgr("aa");
        
    }
    
}
