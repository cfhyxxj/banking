package com.package_7;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI extends JFrame implements ActionListener{
	JButton jb1,jb2,jb3,jb4=null;
	JPanel jp1,jp2,jp3,jp4=null; 
	public UI(){
		 //�������  
	    jb1=new JButton("����");  
	    jb2=new JButton("ת��");  
	    jb3=new JButton("����"); 
	    jb4=new JButton("����");
	    
	    //���ü���  
	    jb1.addActionListener(this);  
	    jb2.addActionListener(this);  
	    jb3.addActionListener(this); 
	    jb4.addActionListener(this); 
	    
	    jp1 = new JPanel();
	    jp2 = new JPanel();
	    jp3 = new JPanel();
	    jp4 = new JPanel();
	    
	    
	    jp1.add(jb1);
	    jp2.add(jb2);
	    jp3.add(jb3);
	    jp4.add(jb4);
	    
	    this.add(jp1);
	    this.add(jp2);
	    this.add(jp3);
	    this.add(jp4);
	    
	    this.setLayout(new GridLayout(4,1));  
        //���������ñ���  
        this.setTitle("ATM");  
        //���ô����С  
        this.setSize(300,250);  
        //���ô����ʼλ��  
        this.setLocation(200, 150);  
        //���õ��رմ���ʱ����֤JVMҲ�˳�  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        //��ʾ����  
        this.setVisible(true);  
        this.setResizable(true);  
	      
	}
	public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand()=="����"){
			dispose(); 
			UI_account ui_account = new UI_account();
			
		}else if(e.getActionCommand()=="ת��"){
			dispose();
		    UI_transfer ui_transfer = new UI_transfer();
		}else if(e.getActionCommand()=="����"){
			dispose(); 
			UI_loan ui_loan = new UI_loan();
		}else if(e.getActionCommand()=="����"){
			dispose(); 
			UI_pay ui_pay = new UI_pay();
		}
	}
	
}
