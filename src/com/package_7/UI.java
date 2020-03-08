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
		 //创建组件  
	    jb1=new JButton("查存款");  
	    jb2=new JButton("转账");  
	    jb3=new JButton("贷款"); 
	    jb4=new JButton("还款");
	    
	    //设置监听  
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
        //给窗口设置标题  
        this.setTitle("ATM");  
        //设置窗体大小  
        this.setSize(300,250);  
        //设置窗体初始位置  
        this.setLocation(200, 150);  
        //设置当关闭窗口时，保证JVM也退出  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        //显示窗体  
        this.setVisible(true);  
        this.setResizable(true);  
	      
	}
	public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand()=="查存款"){
			dispose(); 
			UI_account ui_account = new UI_account();
			
		}else if(e.getActionCommand()=="转账"){
			dispose();
		    UI_transfer ui_transfer = new UI_transfer();
		}else if(e.getActionCommand()=="贷款"){
			dispose(); 
			UI_loan ui_loan = new UI_loan();
		}else if(e.getActionCommand()=="还款"){
			dispose(); 
			UI_pay ui_pay = new UI_pay();
		}
	}
	
}
