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

public class UI_loan extends JFrame implements ActionListener {
	JLabel jlb = null;
	JTextField jtf = null;
	JButton jb = null;
	JPanel jp,jp2 = null;
	
	DefaultTableModel model= null;  
    JTable table = null;  
    JScrollPane jsp = null;
    
	public UI_loan(){
		jlb = new JLabel("���������Ľ�");
		jtf = new JTextField(10);
		jb = new JButton("����");
		jb.addActionListener(this);
		jp = new JPanel();
		jp2 = new JPanel();
		jp.add(jlb);
		jp.add(jtf);
		jp.add(jb);
		this.add(jp);	//
		
		String[] colnames = { "�����˺�","����","������","ʱ��"};  
        model = new DefaultTableModel(colnames, 3);  
        table = new JTable(model);  
        jsp = new JScrollPane(table);
		jp2.add(jsp);
		this.add(jp2);
		//jp.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		this.setLayout(new GridLayout(2, 1));  
	    this.setTitle("����");  
	    this.setSize(500, 400);  
	    this.setLocation(150, 150);  
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    this.setVisible(true);  
	    this.setResizable(true); 
	}
	@Override  
    public void actionPerformed(ActionEvent e) {  
  
        if (e.getActionCommand().equals("����")  &&!jtf.getText().isEmpty()) {                         
        	GetSQL.ConnectSQL();  
        	//System.out.println("1***********************");
        	GetSQL.loan(jtf.getText());
        	//System.out.println("2***********************");
        	//System.out.println("***********************");
              
            
           // jtf3.setText("");
            JOptionPane.showMessageDialog(null, "����ɹ����r(�s���t)�q", "����",JOptionPane.PLAIN_MESSAGE);
//          System.out.println(GetSQL.english);  
//          System.out.println(GetSQL.chinese);  
            // ������������    
            table.setValueAt(GetSQL.loan_number, 0, 0);    
            table.setValueAt(GetSQL.branch_name, 0, 1);
            table.setValueAt(jtf.getText(), 0, 2);
            
            Calendar calendar= Calendar.getInstance();
            //��ת��ʱ��
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
            table.setValueAt(dateFormat.format(calendar.getTime()), 0, 3);
        }else  
        {  
            JOptionPane.showMessageDialog(null , "�����������","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);  
        }  
  
    }  
	
}
