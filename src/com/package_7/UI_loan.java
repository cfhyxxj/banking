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
		jlb = new JLabel("请输入贷款的金额：");
		jtf = new JTextField(10);
		jb = new JButton("贷款");
		jb.addActionListener(this);
		jp = new JPanel();
		jp2 = new JPanel();
		jp.add(jlb);
		jp.add(jtf);
		jp.add(jb);
		this.add(jp);	//
		
		String[] colnames = { "贷款账号","银行","贷款金额","时间"};  
        model = new DefaultTableModel(colnames, 3);  
        table = new JTable(model);  
        jsp = new JScrollPane(table);
		jp2.add(jsp);
		this.add(jp2);
		//jp.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		this.setLayout(new GridLayout(2, 1));  
	    this.setTitle("贷款");  
	    this.setSize(500, 400);  
	    this.setLocation(150, 150);  
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    this.setVisible(true);  
	    this.setResizable(true); 
	}
	@Override  
    public void actionPerformed(ActionEvent e) {  
  
        if (e.getActionCommand().equals("贷款")  &&!jtf.getText().isEmpty()) {                         
        	GetSQL.ConnectSQL();  
        	//System.out.println("1***********************");
        	GetSQL.loan(jtf.getText());
        	//System.out.println("2***********************");
        	//System.out.println("***********************");
              
            
           // jtf3.setText("");
            JOptionPane.showMessageDialog(null, "贷款成功！╮(╯▽╰)╭", "贷款",JOptionPane.PLAIN_MESSAGE);
//          System.out.println(GetSQL.english);  
//          System.out.println(GetSQL.chinese);  
            // 将数据填入表格    
            table.setValueAt(GetSQL.loan_number, 0, 0);    
            table.setValueAt(GetSQL.branch_name, 0, 1);
            table.setValueAt(jtf.getText(), 0, 2);
            
            Calendar calendar= Calendar.getInstance();
            //设转账时间
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
            table.setValueAt(dateFormat.format(calendar.getTime()), 0, 3);
        }else  
        {  
            JOptionPane.showMessageDialog(null , "请输入贷款金额","提示消息",JOptionPane.WARNING_MESSAGE);  
        }  
  
    }  
	
}
