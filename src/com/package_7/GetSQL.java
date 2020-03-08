
/* 
 * 功能：用来和数据库SQLserver进行连接，以及相应的查询方法。 
 */  
package com.package_7;  
  
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;  
  
//写一个类，用来与数据库建立连接，并且查询数据  
public class GetSQL {  
    // 设定用户名和密码  
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
  
    // 用于连接数据库的方法，可用于子类的继承  
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
  
    // 用于向数据库进行查询的方法  
    public static void query(String username) {  
        // 创建火箭车  
        try {  
            ps = ct.prepareStatement("select * from info where account_number=?");  
            // 给?赋值(可防止SQL注入漏洞问题)，不要直接使用拼接的方式    
            ps.setString(1, username);  
            // ResultSet结果集,大家可以把ResultSet理解成返回一张表行的结果集  
            rs = ps.executeQuery();  
            // 循环取出  
            if (rs.next()) {  
                // 将教师的用户名和密码取出  
                userword = rs.getString(1);  
                pwd = rs.getString(2);  
//                System.out.println("成功获取到用户名和密码 from数据库");  
//                System.out.println(userword + "\t" + pwd + "\t");  
            }else  
            {  
                JOptionPane.showMessageDialog(null, "没有此用户，请重新输入！", "提示消息", JOptionPane.WARNING_MESSAGE);  
            }  
        } catch (Exception e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }  
    }
    public static void account_query(String username) {  
        // 创建火箭车  
        try {  
            ps = ct.prepareStatement("select * from account where account_number=?");  
            // 给?赋值(可防止SQL注入漏洞问题)，不要直接使用拼接的方式    
            ps.setString(1, username);  
            // ResultSet结果集,大家可以把ResultSet理解成返回一张表行的结果集  
            rs = ps.executeQuery();  
            // 循环取出  
            if (rs.next()) {  
                // 将教师的用户名和密码取出  
                userword = rs.getString(1);  
                branch_name = rs.getString(2);  
                balance = rs.getString(3);
               
            
                 
            }else  
            {  
                JOptionPane.showMessageDialog(null, "没有此用户，请重新输入！", "提示消息", JOptionPane.WARNING_MESSAGE);  
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
           // ps1.registerOutParameter(1, java.sql.Types.INTEGER);//CallableStatement ps1 返回状态
            
          //  ps = ct.prepareStatement("{call dbo.PTransfer(？，？，？)}");
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
			//关闭资源
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
        // 创建火箭车  
        try {  
        	account_query(userword);
        	System.out.println(userword);
        	
            ps = ct.prepareStatement("INSERT INTO loan VALUES (?,?,?,?);");  
            
            System.out.println(userword);
            // 给?赋值(可防止SQL注入漏洞问题)，不要直接使用拼接的方式    
            ps.setString(1,"888"+userword);  
            ps.setString(2,branch_name); 
            ps.setString(3,amount); 
            ps.setString(4,"");
           // System.out.println("1");
            // ResultSet结果集,大家可以把ResultSet理解成返回一张表行的结果集  
            int count =ps.executeUpdate(); 
            //System.out.println("2");
            loan_number =  "888"+userword;
                
           
        } catch (Exception e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }finally{
			//关闭资源
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
           // ps1.registerOutParameter(1, java.sql.Types.INTEGER);//CallableStatement ps1 返回状态
            
          //  ps = ct.prepareStatement("{call dbo.PTransfer(？，？，？)}");
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
			//关闭资源
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
    // 创建火箭车  
    try {  
        ps = ct.prepareStatement("select amount from loan where loan_number=?");  
        // 给?赋值(可防止SQL注入漏洞问题)，不要直接使用拼接的方式    
        ps.setString(1, y);  
        // ResultSet结果集,大家可以把ResultSet理解成返回一张表行的结果集  
        rs = ps.executeQuery();  
        // 循环取出  
        if (rs.next()) {  
            // 将教师的用户名和密码取出  
            loan_amount = rs.getString(1);  
         //   branch_name = rs.getString(2);  
        //    balance = rs.getString(3);
           
        
             
        }else  
        {  
            JOptionPane.showMessageDialog(null, "没有此用户，请重新输入！", "提示消息", JOptionPane.WARNING_MESSAGE);  
        }  
    } catch (Exception e1) {  
        // TODO Auto-generated catch block  
        e1.printStackTrace();  
    } finally{
		//关闭资源
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
    // 创建火箭车
		
		try {  
			ps = ct.prepareStatement("select * from loan where loan_number=?");  
        // 给?赋值(可防止SQL注入漏洞问题)，不要直接使用拼接的方式    
			ps.setString(1, y);  
        // ResultSet结果集,大家可以把ResultSet理解成返回一张表行的结果集  
			rs = ps.executeQuery();  
        // 循环取出  
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
		//关闭资源
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
