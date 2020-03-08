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

public class UI_transfer extends JFrame implements ActionListener {
	JLabel jl1,jl2,jl3 = null;  
    JTextField jtf1,jtf2 = null;  
    JButton jb1,jb2,jb3 = null;  
    JPanel jp1, jp2,jp3,jp4,jp5 = null;  
  
    DefaultTableModel model= null;  
    JTable table = null;  
    JScrollPane jsp = null;
    public UI_transfer(){
    	jl1 = new JLabel("������Ŀ���ʻ���");
    	jl2 = new JLabel("������ת�˽�");
    	jl3 = new JLabel("ת�˺��˻� ��Ϣ��");
    	
    	jtf1 = new JTextField(10);
    	jtf2 = new JTextField(10);
    	
    	jb1 = new JButton("ת��");
    	jb2 = new JButton("����");
    	jb3 = new JButton("�˳�");
    	jb1.addActionListener(this);  
    	jb2.addActionListener(this);  
    	jb3.addActionListener(this); 
    	
    	String[] colnames = { "�˺�", "��������", "���","ʱ��"};  
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
        this.add(jp4);  //add��˳�������ҳ����ֵ�˳��
        this.add(jp5);  
       // this.add(jp6);  
          
        this.setLayout(new GridLayout(5, 1));  
        this.setTitle("ת��ϵͳ");  
        this.setSize(500, 500);  
        this.setLocation(150, 150);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setVisible(true);  
        this.setResizable(true); 
        
    	
    }
    @Override  
    public void actionPerformed(ActionEvent e) {  
  
        if (e.getActionCommand().equals("ת��")  &&!jtf1.getText().isEmpty()&&!jtf2.getText().isEmpty()) {                         
        	GetSQL.ConnectSQL();  
        	System.out.println("1***********************");
        	GetSQL.transfer(jtf1.getText(),jtf2.getText());
        	System.out.println("2***********************");
        	//System.out.println("***********************");
              
            
           // jtf3.setText("");
            JOptionPane.showMessageDialog(null, "ת�˳ɹ����r(�s���t)�q", "ת��",JOptionPane.PLAIN_MESSAGE);
//          System.out.println(GetSQL.english);  
//          System.out.println(GetSQL.chinese);  
            // ������������    
            table.setValueAt(GetSQL.userword, 0, 0);  
            table.setValueAt(GetSQL.branch_name, 0, 1);  
            table.setValueAt(GetSQL.balance, 0, 2); 
            Calendar calendar= Calendar.getInstance();
            //��ת��ʱ��
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
            table.setValueAt(dateFormat.format(calendar.getTime()), 0, 3);
        }else if (e.getActionCommand().equals("����")){
        	jtf1.setText(""); 
            jtf2.setText("");
            //jtf3.setText("");
        	
        }else if (e.getActionCommand().equals("�˳�")){
        	dispose();
        }else  
        {  
            JOptionPane.showMessageDialog(null , "������������Ϣ","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);  
        }  
  
    }  
}
