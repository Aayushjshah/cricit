package com.aayush;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.Image;
import javax.swing.JOptionPane;;



public class CreateTournament extends JPanel implements ActionListener{
    public JPanel myPanel = new JPanel();
    public JScrollPane jsp = new JScrollPane(myPanel);
//Addpolicy
JLabel[] arr = new JLabel[5];
    String[] labels ={"Tournament Name","Format","Total Teams" , "Year","Overs"};
    JTextField[] tarr = new JTextField[5];
    JButton back,next;
    JLabel image;
    FontPicker fp = new FontPicker();
    int i;
    String userName;

CardLayoutMgr clm;
    CreateTournament(CardLayoutMgr clm,String username){
       //inital db connect to fill up dropdowns
       this.clm=clm;
       userName=username;
    //    Conn c = new Conn();
    //    try{
    //        //dbConnect
    //    }catch(Exception e){
    //        System.out.println("Initial connect AddPolicy");
    //    }
//oanel Settings
        myPanel.setLayout(null);
        myPanel.setBackground(Color.WHITE);
        jsp.setBorder(new EmptyBorder(0,0,0,0));

//subItems
        JLabel head = new JLabel("<html><u>Create Tournament</u></html>");
        head.setBounds(240,5,400,40);
        head.setFont(fp.headFont);
        head.setForeground(Color.BLACK);
        myPanel.add(head);

        int x=160;
        for(i=0;i<5;i++){
            arr[i]=new JLabel(labels[i]);
            arr[i].setFont(fp.forLabel);
            arr[i].setForeground(Color.BLACK);
            arr[i].setBounds(20,x,150,30);
            arr[i].setVisible(true);
            myPanel.add(arr[i]);

            tarr[i]= new JTextField();
            tarr[i].setFont(fp.forLabel);
            tarr[i].setForeground(Color.BLACK);
            tarr[i].setBounds(175,x,150,30);
            myPanel.add(tarr[i]);
            x+=50;
        }
        x=60;
        //Image
        int ht=450,wdt=470;
        ImageIcon i1 = new ImageIcon(this.getClass().getResource("/com/aayush/icons/ocean.jpg"));
        Image i2 = i1.getImage().getScaledInstance(wdt,ht,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        image = new JLabel(i3);
        image.setBounds(340,80,wdt,ht);
        myPanel.add(image);
        image.setVisible(true);
        // image.setVisible(false);


        back = new JButton("<html><u>Back</u></html>");
        back.setFont(fp.forLabel);
        back.setForeground(Color.WHITE);
        back.setBackground(Color.BLACK);
        back.addActionListener(this);
        back.setBounds(485,570,150,30);
        myPanel.add(back);


        next = new JButton("<html><u>Create Tournament</u></html>");
        next.setFont(fp.forLabel);
        next.setForeground(Color.WHITE);
        next.setBackground(Color.BLACK);
        next.addActionListener(this);
        next.setBounds(655,570,150,30);
        myPanel.add(next);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == back){
            System.out.println("BACK PRESSED");
            
        }else if(ae.getSource() == next){
            //one array for policyDetails
            String Tournamentquery = "insert into tournament(name,format,tot_teams,year,team_won_id,user_id,overs) values('";
            Tournamentquery+=tarr[0].getText()+"','";
            Tournamentquery+=tarr[1].getText()+"',";
            Tournamentquery+=Integer.parseInt(tarr[2].getText())+",";
            Tournamentquery+=Integer.parseInt(tarr[3].getText())+",null,'";
            Tournamentquery+=userName+"',";
            Tournamentquery+=Integer.parseInt(tarr[4].getText())+")";
            System.out.println(Tournamentquery);
            try{
                Conn c = new Conn();
                c.s.executeUpdate(Tournamentquery);
                ResultSet rs = c.s.executeQuery("select last_insert_id()");
                rs.next();
                String notify ="Tournament Created SucessFully. \n Your tour_id is "+rs.getInt(1)+"\n Save this for fututre reference.";
                JOptionPane.showMessageDialog(null , notify);
                //go to createTeam page
                // we will pass no_ofTeams and tour_id.
            }catch(Exception e){
                e.printStackTrace();
            }
            // append to add teams
            this.setVisible(false);
        }
    }    
}
