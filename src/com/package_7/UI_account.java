package com.package_7;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UI_account extends JFrame implements ActionListener{
//JLabel jbl = null;
//JTextField jtf = null;
//JButton jb = null;
JPanel jp = null;

DefaultTableModel model = null;  
JTable table = null;  
JScrollPane jsp = null;  
public UI_account(){
//	//jbl = new JLabel("��������Ҫ��ѯ���˺ţ�");
//	jtf = new JTextField(10);
//	jb = new JButton("��ѯ");
//	
	//jb.addActionListener(this);
	
	String[] colnames = { "�˺�", "��������", "���"};  
    model = new DefaultTableModel(colnames, 3);  
    table = new JTable(model);  
    jsp = new JScrollPane(table);  
    
    jp =new JPanel();
   // jp2 =new JPanel();
    
//    jp1.add(jbl);
//    jp1.add(jtf);
//    jp1.add(jb);
    //jp1.setLayout(new FlowLayout(FlowLayout.LEFT));  //����jp1������
    
    jp.add(jsp);
    
    this.add(jp);
   // this.add(jp2);
    
    this.setLayout(new GridLayout(1, 1));  
    this.setTitle("��������");  
    this.setSize(500, 500);  
    this.setLocation(150, 150);  
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setVisible(true);  
    this.setResizable(true); 
    GetSQL.ConnectSQL();  
    GetSQL.account_query(GetSQL.userword);  
    table.setValueAt(GetSQL.userword, 0, 0);  
    table.setValueAt(GetSQL.branch_name, 0, 1);  
    table.setValueAt(GetSQL.balance, 0, 2);                   
 }        
  
//    @Override  
   public void actionPerformed(ActionEvent e) {  
//  
//        if (e.getActionCommand().equals("��ѯ") &&!jtf.getText().isEmpty()) {                         
//            // �������ѯ��ť1ʱ�����������ݿ⽨������  
//            GetSQL.ConnectSQL();  
//            GetSQL.account_query(jtf.getText());  
//            jtf.setText("");  
////          System.out.println(GetSQL.english);  
////          System.out.println(GetSQL.chinese);  
//            // ������������    
//            table.setValueAt(GetSQL.userword, 0, 0);  
//            table.setValueAt(GetSQL.branch_name, 0, 1);  
//            table.setValueAt(GetSQL.balance, 0, 2);                   
//                  
//        }else{  
//            JOptionPane.showMessageDialog(null , "������Ҫ��ѯ�ı��","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);  
//        }  
   }
}  



