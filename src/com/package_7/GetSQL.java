
/* 
 * ���ܣ����������ݿ�SQLserver�������ӣ��Լ���Ӧ�Ĳ�ѯ������ 
 */  
package com.package_7;  
  
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;  
  
//дһ���࣬���������ݿ⽨�����ӣ����Ҳ�ѯ����  
public class GetSQL {  
    // �趨�û���������  
	static String userword; 
    static String pwd;  
      
    static String branch_name;  
    static String balance;  
    
    static String loan_number;  
    static String loan_amount;
    
    static String temp_loan_amount;
    
    static Connection ct = null;  
    static PreparedStatement ps = null;  
    static ResultSet rs = null;  
  
    // �����������ݿ�ķ���������������ļ̳�  
    public static void ConnectSQL() {  
        try {  
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=banking", "sa", "chenlipen12");
            System.out.println("The SQL is connected");  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
    }  
  
    // ���������ݿ���в�ѯ�ķ���  
    public static void query(String username) {  
        // ���������  
        try {  
            ps = ct.prepareStatement("select * from info where account_number=?");  
            // ��?��ֵ(�ɷ�ֹSQLע��©������)����Ҫֱ��ʹ��ƴ�ӵķ�ʽ    
            ps.setString(1, username);  
            // ResultSet�����,��ҿ��԰�ResultSet���ɷ���һ�ű��еĽ����  
            rs = ps.executeQuery();  
            // ѭ��ȡ��  
            if (rs.next()) {  
                // ����ʦ���û���������ȡ��  
                userword = rs.getString(1);  
                pwd = rs.getString(2);  
//                System.out.println("�ɹ���ȡ���û��������� from���ݿ�");  
//                System.out.println(userword + "\t" + pwd + "\t");  
            }else  
            {  
                JOptionPane.showMessageDialog(null, "û�д��û������������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);  
            }  
        } catch (Exception e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }  
    }
    public static void account_query(String username) {  
        // ���������  
        try {  
            ps = ct.prepareStatement("select * from account where account_number=?");  
            // ��?��ֵ(�ɷ�ֹSQLע��©������)����Ҫֱ��ʹ��ƴ�ӵķ�ʽ    
            ps.setString(1, username);  
            // ResultSet�����,��ҿ��԰�ResultSet���ɷ���һ�ű��еĽ����  
            rs = ps.executeQuery();  
            // ѭ��ȡ��  
            if (rs.next()) {  
                // ����ʦ���û���������ȡ��  
                userword = rs.getString(1);  
                branch_name = rs.getString(2);  
                balance = rs.getString(3);
               
            
                 
            }else  
            {  
                JOptionPane.showMessageDialog(null, "û�д��û������������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);  
            }  
        } catch (Exception e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }  
    }
    
    public static void transfer(String y,String amount) {  
        
        try {  
        	
        	
        	 
        	//cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
        	
        	CallableStatement ps1 = ct.prepareCall("{call dbo.PTransfer(?,?,?)}");   
           // ps1.registerOutParameter(1, java.sql.Types.INTEGER);//CallableStatement ps1 ����״̬
            
          //  ps = ct.prepareStatement("{call dbo.PTransfer(����������)}");
            ps1.setString(1,userword);
            System.out.println(userword);
            ps1.setString(2,y);
            System.out.println(y);
            ps1.setString(3,amount);
            System.out.println(amount);
            //int count = cs.executeUpdate();
           // rs = ps.executeQuery();
            int count =ps1.executeUpdate();
            System.out.println(count);
            //System.out.println("3********************************");
            account_query(userword);
        }
         catch (Exception e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }finally{
			//�ر���Դ
			if(ct != null){
				try {
					ct.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        }
    }
    public static void loan(String amount) {  
        // ���������  
        try {  
        	account_query(userword);
        	System.out.println(userword);
        	
            ps = ct.prepareStatement("INSERT INTO loan VALUES (?,?,?,?);");  
            
            System.out.println(userword);
            // ��?��ֵ(�ɷ�ֹSQLע��©������)����Ҫֱ��ʹ��ƴ�ӵķ�ʽ    
            ps.setString(1,"888"+userword);  
            ps.setString(2,branch_name); 
            ps.setString(3,amount); 
            ps.setString(4,"");
           // System.out.println("1");
            // ResultSet�����,��ҿ��԰�ResultSet���ɷ���һ�ű��еĽ����  
            int count =ps.executeUpdate(); 
            //System.out.println("2");
            loan_number =  "888"+userword;
                
           
        } catch (Exception e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }finally{
			//�ر���Դ
			if(ct != null){
				try {
					ct.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
     }
  }
public static void pay(String y,String amount) {  
        
        try {  
        	temp_loan_amount =   loan_amount;
        	
        	//cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
        	
        	CallableStatement ps1 = ct.prepareCall("{call dbo.SPayment(?,?,?)}");
        //	System.out.println("3********************************");
           // ps1.registerOutParameter(1, java.sql.Types.INTEGER);//CallableStatement ps1 ����״̬
            
          //  ps = ct.prepareStatement("{call dbo.PTransfer(����������)}");
            ps1.setString(1,userword);
          // // System.out.println(userword);
            ps1.setString(2,y);
           // System.out.println(y);
            ps1.setString(3,amount);
           // System.out.println(amount);
            //int count = cs.executeUpdate();
           // rs = ps.executeQuery();
         //   System.out.println("44********************************");
            //rs = ps1.executeQuery();
            int count =ps1.executeUpdate();
         //   System.out.println("4********************************");
            loan_amount_query(y);
            //System.out.println(" 5********************************");
            //System.out.println("3********************************");
            //account_query(userword);
        }
         catch (Exception e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }finally{
			//�ر���Դ
			if(ct != null){
				try {
					ct.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        }
    }
public static void loan_amount_query(String y) {  
    // ���������  
    try {  
        ps = ct.prepareStatement("select amount from loan where loan_number=?");  
        // ��?��ֵ(�ɷ�ֹSQLע��©������)����Ҫֱ��ʹ��ƴ�ӵķ�ʽ    
        ps.setString(1, y);  
        // ResultSet�����,��ҿ��԰�ResultSet���ɷ���һ�ű��еĽ����  
        rs = ps.executeQuery();  
        // ѭ��ȡ��  
        if (rs.next()) {  
            // ����ʦ���û���������ȡ��  
            loan_amount = rs.getString(1);  
         //   branch_name = rs.getString(2);  
        //    balance = rs.getString(3);
           
        
             
        }else  
        {  
            JOptionPane.showMessageDialog(null, "û�д��û������������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);  
        }  
    } catch (Exception e1) {  
        // TODO Auto-generated catch block  
        e1.printStackTrace();  
    } finally{
		//�ر���Դ
		if(ct != null){
			try {
				ct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    } 
}
	public static int loan_number_query(String y){  
    // ���������
		
		try {  
			ps = ct.prepareStatement("select * from loan where loan_number=?");  
        // ��?��ֵ(�ɷ�ֹSQLע��©������)����Ҫֱ��ʹ��ƴ�ӵķ�ʽ    
			ps.setString(1, y);  
        // ResultSet�����,��ҿ��԰�ResultSet���ɷ���һ�ű��еĽ����  
			rs = ps.executeQuery();  
        // ѭ��ȡ��  
			if (rs.next()) {  
				return 1;
         //   branch_name = rs.getString(2);  
        //    balance = rs.getString(3);
             }else{  
				return 0;
			}  
		} catch (Exception e1) {  
			// TODO Auto-generated catch block  
			e1.printStackTrace(); 
			return 0;
			} finally{
		//�ر���Դ
		if(ct != null){
			try {
				ct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    } 
}
}
