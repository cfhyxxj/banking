/* 
 * 功能：学生成绩管理系统 
 * 步骤1、登录界面的静态实现 
 * 步骤2：实现界面的切换 
 * 步骤3：使用数据库来验证用户名和密码 
 * 步骤4：对代码进行优化。增加专门用来与数据库进行连接的类 
 * 步骤5：优化代码，增加判断条件。 
 * 步骤6：使用数据库进行查询时，优化查询方法和判断条件。数据库的表中可有多个数据。引入不同的表来查询。 
 * 步骤7：教师界面实现了查询某个学生信息和某教师信息的功能。 
 * author：Lipeng Chen 
 */  
package com.package_7;  
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;  
  
public class Login extends JFrame implements ActionListener {  
  
    //定义登录界面的组件  
    JButton jb1,jb2,jb3=null;  
    JPanel jp1,jp2,jp3=null;  
    JTextField jtf=null;  
    JLabel jlb1,jlb2=null;  
    JPasswordField jpf=null;        
      
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        Login ms=new Login();  
          
                          
    }  
    //构造函数  
    public Login()  
    {  
         //创建组件  
        jb1=new JButton("登录");  
        jb2=new JButton("重置");  
        jb3=new JButton("退出");  
        //设置监听  
        jb1.addActionListener(this);  
        jb2.addActionListener(this);  
        jb3.addActionListener(this);  
         
        jp1=new JPanel();  
        jp2=new JPanel();  
        jp3=new JPanel();                  
          
        jlb1=new JLabel("账号：");  
        jlb2=new JLabel("密    码：");   
          
        jtf=new JTextField(10);  
        jpf=new JPasswordField(10);  
        //加入到JPanel中  
        jp1.add(jlb1);  
        jp1.add(jtf);  
          
        jp2.add(jlb2);  
        jp2.add(jpf);  
          
        jp3.add(jb1);  
        jp3.add(jb2);
        jp3.add(jb3);
          
       
          
        //加入JFrame中   
        this.add(jp1);  
        this.add(jp2);  
        this.add(jp3);  
        //设置布局管理器  
        this.setLayout(new GridLayout(3,1));  
        //给窗口设置标题  
        this.setTitle("ATM存取款机");  
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
    @Override  
    public void actionPerformed(ActionEvent e) {  
  
        if(e.getActionCommand()=="退出")  
        {  
            System.exit(0);  
        }
        else if(e.getActionCommand()=="登录")  
        {  
            if(!jtf.getText().isEmpty() && !jpf.getText().isEmpty())  
            {  
                //当点击登录按钮时，首先与数据库建立连接  
                GetSQL.ConnectSQL();  
                //如果选中教师登录  
                GetSQL.query(jtf.getText());  
                  //首先判断是否存在该用户，即是否得到了密码  
                if(GetSQL.pwd ==null)  
                {  
                	this.clear();  
                }else  
                {  
                 //调用登录方法  
                	this.enter();  
                }  
               
              }
            else if(jtf.getText().isEmpty())  
            {  
                JOptionPane.showMessageDialog(null,"请输入用户名","提示消息",JOptionPane.WARNING_MESSAGE);  
                this.clear();  
            }
            else if(jpf.getText().isEmpty())     
            {  
                JOptionPane.showMessageDialog(null,"请输入密码","提示消息",JOptionPane.WARNING_MESSAGE);  
                this.clear();  
            }  
        }
        else if(e.getActionCommand()=="重置")  
        {  
            this.clear();  
        }             
          
    }  
                  
        //清空文本框和密码框  
    public  void clear()  
        {  
            jtf.setText("");  
            jpf.setText("");  
        }  
            //学生登录判断方法  
    public void enter()  
    {  
        if(GetSQL.pwd.equals(jpf.getText()))  
        {  
//          System.out.println("登录成功");  
             JOptionPane.showMessageDialog(null,"登录成功！","提示消息",JOptionPane.PLAIN_MESSAGE);  
             this.clear();    
            //关闭当前界面  
             dispose();  
            // System.out.println("00000000000000000000000000000000");
             //创建一个新界面，适用于教师来管理学生  
             UI ui=new UI(); 
            // System.out.println("00000000000000000000000000000000");
        }else if(jtf.getText().isEmpty()&&jpf.getText().isEmpty())  
        {  
            JOptionPane.showMessageDialog(null,"请输入用户名和密码！","提示消息",JOptionPane.WARNING_MESSAGE);  
        }else if(jtf.getText().isEmpty())  
        {  
            JOptionPane.showMessageDialog(null,"请输入用户名！","提示消息",JOptionPane.WARNING_MESSAGE);  
        }else if(jpf.getText().isEmpty())  
        {  
            JOptionPane.showMessageDialog(null,"请输入密码！","提示消息",JOptionPane.WARNING_MESSAGE);  
        }else  
        {  
            JOptionPane.showMessageDialog(null,"用户名或者密码错误！\n请重新输入","提示消息",JOptionPane.ERROR_MESSAGE);  
            //清空输入框  
            this.clear();  
        }  
    }  
  
          
}  