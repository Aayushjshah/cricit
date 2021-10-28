package com.aayush;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.Image;

// import javax.smartcardio.Card;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
// import javax.swing
public class VerticalNav extends JPanel implements ActionListener,MouseInputListener{
    public JPanel p1 = new JPanel();
    JButton[] lArr = new JButton[5];
    String[] labels = {"View Tournaments","Create Tournament","Modify Tournament","Delete Tournament","Manage Reminders"};
    FontPicker fp = new FontPicker();
    CardLayoutMgr clm;
    JLabel back,next; 
        VerticalNav(CardLayoutMgr cll){
            clm=cll;
        p1.setBackground(new Color(230, 255, 230));
        p1.setPreferredSize(new Dimension(220,150));
        p1.setVisible(true);
        p1.setBounds(0,0,220,650);
        p1.setLayout(null);
        // add(p1,BorderLayout.WEST);back-button.png
        ImageIcon i1 = new ImageIcon(this.getClass().getResource("/com/aayush/icons/ocean.jpg"));
        Image i2 = i1.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        back= new JLabel(i3);
        back.setBounds(55,5,40,50);
        back.addMouseListener(this);
        p1.add(back);

        ImageIcon ii1 = new ImageIcon(this.getClass().getResource("/com/aayush/icons/ocean.jpg"));
        Image ii2 = ii1.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        ImageIcon ii3 = new ImageIcon(ii2);
        next = new JLabel(ii3);
        next.addMouseListener(this);
        next.setBounds(105,5,40,50);
        
        p1.add(next);
        int j=100;
        int i=0;
        for(;i<5;i++){
            lArr[i]=new JButton(labels[i]);
            lArr[i].setFont(fp.forLabel);
            lArr[i].setForeground(Color.WHITE);
            lArr[i].setBackground(Color.BLACK);
            lArr[i].addActionListener(this);
            lArr[i].setBounds(10, j, 200, 30);
            p1.add(lArr[i]);
            j+=50;
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] panelIds = {"scl","apm","pdd1","upm","pdd1"};
        for(int i=0;i<5;i++){
            if(e.getSource()==lArr[i]){
                // clm.setr(panelIds[i]);
            }
            
        }
        
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == back){
            System.out.println("BACK");
        }else if(e.getSource() == next){
            System.out.println("NEXT");
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
