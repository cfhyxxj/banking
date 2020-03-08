package com.package_7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UI_pay extends JFrame implements ActionListener{
	static int temp;
	JLabel jl1,jl2,jl3 = null;  
    JTextField jtf1,jtf2 = null;  
    JButton jb1,jb2,jb3 = null;  
    JPanel jp1, jp2,jp3,jp4,jp5 = null;  
  
    DefaultTableModel model= null;  
    JTable table = null;  
    JScrollPane jsp = null;
    public UI_pay(){
    	jl1 = new JLabel("请输入您的贷款账号：");
    	jl2 = new JLabel("请输入还款金额：");
    	jl3 = new JLabel("本次还款信息：");
    	
    	jtf1 = new JTextField(10);
    	jtf2 = new JTextField(10);
    	
    	jb1 = new JButton("还款");
    	jb2 = new JButton("重置");
    	jb3 = new JButton("退出");
    	jb1.addActionListener(this); 
    	jb2.addActionListener(this);
    	jb3.addActionListener(this);
    	
    	
    	String[] colnames = { "贷款账号", "还款账号", "还款余额","还款时间"};  
        model = new DefaultTableModel(colnames, 3);  
        table = new JTable(model);  
        jsp = new JScrollPane(table);
        
        jp1 = new JPanel();  
        jp2 = new JPanel();  
        jp3 = new JPanel();  
        jp4 = new JPanel();  
        jp5 = new JPanel();   
        jp4.setLayout(new BorderLayout());
        
        jp1.add(jl1);
        jp1.add(jtf1);
       // jp1.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        jp2.add(jl2);
        jp2.add(jtf2);
       // jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        jp3.add(jb1);
        jp3.add(jb2);
        jp3.add(jb3);
       // jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
           
        jp4.add(jl3,BorderLayout.SOUTH);
        
        jp5.add(jsp);
        
        this.add(jp1);  
        this.add(jp2);  
        this.add(jp3);  
        this.add(jp4);  //add的顺序决定在页面呈现的顺序
        this.add(jp5);  
       // this.add(jp6);  
          
        this.setLayout(new GridLayout(5, 1));  
        this.setTitle("还款系统");  
        this.setSize(500, 500);  
        this.setLocation(150, 150);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setVisible(true);  
        this.setResizable(true); 
    }  
    @Override  
    public void actionPerformed(ActionEvent e) {  
  
        if (e.getActionCommand().equals("还款") &&!jtf1.getText().isEmpty()&&!jtf2.getText().isEmpty() && GetSQL.loan_number_query(jtf1.getText())>0) {           
        	GetSQL.ConnectSQL();  
            
        	//System.out.println("1***********************");
        	GetSQL.pay(jtf1.getText(),jtf2.getText());
        	//System.out.println("2***********************");
        	//System.out.println("***********************");
        	table.setValueAt(jtf1.getText(), 0, 0);  
            table.setValueAt(GetSQL.userword, 0, 1);  
            table.setValueAt(jtf2.getText(), 0, 2); 
            
            Calendar calendar= Calendar.getInstance();
            //设转账时间
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
            table.setValueAt(dateFormat.format(calendar.getTime()), 0, 3);
            
            temp = Integer.valueOf(GetSQL.temp_loan_amount).intValue()-Integer.valueOf(jtf2.getText()).intValue();
            
            if(temp>0){
        		JOptionPane.showMessageDialog(null, "本次还款成功！╮(╯▽╰)╭,您还剩"+temp+"要还", "还款",JOptionPane.PLAIN_MESSAGE);
        	}else if(temp==0){
            	JOptionPane.showMessageDialog(null, "已还清所有贷款！", "还款",JOptionPane.PLAIN_MESSAGE);
            }else if(temp<0){
            	JOptionPane.showMessageDialog(null, "还多了，退"+(-temp)+"元，请清点数量", "还款",JOptionPane.PLAIN_MESSAGE);
            }       
            
        }     
            else if (e.getActionCommand().equals("重置")){
        	jtf1.setText(""); 
            jtf2.setText("");
            //jtf3.setText("");
        	
        }else if (e.getActionCommand().equals("退出")){
        	dispose();
        }else  
        {  
            JOptionPane.showMessageDialog(null , "请输入正确的账号","提示消息",JOptionPane.WARNING_MESSAGE);  
        }  
  
    }  
}
