/* 
 * ���ܣ�ѧ���ɼ�����ϵͳ 
 * ����1����¼����ľ�̬ʵ�� 
 * ����2��ʵ�ֽ�����л� 
 * ����3��ʹ�����ݿ�����֤�û��������� 
 * ����4���Դ�������Ż�������ר�����������ݿ�������ӵ��� 
 * ����5���Ż����룬�����ж������� 
 * ����6��ʹ�����ݿ���в�ѯʱ���Ż���ѯ�������ж����������ݿ�ı��п��ж�����ݡ����벻ͬ�ı�����ѯ�� 
 * ����7����ʦ����ʵ���˲�ѯĳ��ѧ����Ϣ��ĳ��ʦ��Ϣ�Ĺ��ܡ� 
 * author��Lipeng Chen 
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
  
    //�����¼��������  
    JButton jb1,jb2,jb3=null;  
    JPanel jp1,jp2,jp3=null;  
    JTextField jtf=null;  
    JLabel jlb1,jlb2=null;  
    JPasswordField jpf=null;        
      
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        Login ms=new Login();  
          
                          
    }  
    //���캯��  
    public Login()  
    {  
         //�������  
        jb1=new JButton("��¼");  
        jb2=new JButton("����");  
        jb3=new JButton("�˳�");  
        //���ü���  
        jb1.addActionListener(this);  
        jb2.addActionListener(this);  
        jb3.addActionListener(this);  
         
        jp1=new JPanel();  
        jp2=new JPanel();  
        jp3=new JPanel();                  
          
        jlb1=new JLabel("�˺ţ�");  
        jlb2=new JLabel("��    �룺");   
          
        jtf=new JTextField(10);  
        jpf=new JPasswordField(10);  
        //���뵽JPanel��  
        jp1.add(jlb1);  
        jp1.add(jtf);  
          
        jp2.add(jlb2);  
        jp2.add(jpf);  
          
        jp3.add(jb1);  
        jp3.add(jb2);
        jp3.add(jb3);
          
       
          
        //����JFrame��   
        this.add(jp1);  
        this.add(jp2);  
        this.add(jp3);  
        //���ò��ֹ�����  
        this.setLayout(new GridLayout(3,1));  
        //���������ñ���  
        this.setTitle("ATM��ȡ���");  
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
    @Override  
    public void actionPerformed(ActionEvent e) {  
  
        if(e.getActionCommand()=="�˳�")  
        {  
            System.exit(0);  
        }
        else if(e.getActionCommand()=="��¼")  
        {  
            if(!jtf.getText().isEmpty() && !jpf.getText().isEmpty())  
            {  
                //�������¼��ťʱ�����������ݿ⽨������  
                GetSQL.ConnectSQL();  
                //���ѡ�н�ʦ��¼  
                GetSQL.query(jtf.getText());  
                  //�����ж��Ƿ���ڸ��û������Ƿ�õ�������  
                if(GetSQL.pwd ==null)  
                {  
                	this.clear();  
                }else  
                {  
                 //���õ�¼����  
                	this.enter();  
                }  
               
              }
            else if(jtf.getText().isEmpty())  
            {  
                JOptionPane.showMessageDialog(null,"�������û���","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);  
                this.clear();  
            }
            else if(jpf.getText().isEmpty())     
            {  
                JOptionPane.showMessageDialog(null,"����������","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);  
                this.clear();  
            }  
        }
        else if(e.getActionCommand()=="����")  
        {  
            this.clear();  
        }             
          
    }  
                  
        //����ı���������  
    public  void clear()  
        {  
            jtf.setText("");  
            jpf.setText("");  
        }  
            //ѧ����¼�жϷ���  
    public void enter()  
    {  
        if(GetSQL.pwd.equals(jpf.getText()))  
        {  
//          System.out.println("��¼�ɹ�");  
             JOptionPane.showMessageDialog(null,"��¼�ɹ���","��ʾ��Ϣ",JOptionPane.PLAIN_MESSAGE);  
             this.clear();    
            //�رյ�ǰ����  
             dispose();  
            // System.out.println("00000000000000000000000000000000");
             //����һ���½��棬�����ڽ�ʦ������ѧ��  
             UI ui=new UI(); 
            // System.out.println("00000000000000000000000000000000");
        }else if(jtf.getText().isEmpty()&&jpf.getText().isEmpty())  
        {  
            JOptionPane.showMessageDialog(null,"�������û��������룡","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);  
        }else if(jtf.getText().isEmpty())  
        {  
            JOptionPane.showMessageDialog(null,"�������û�����","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);  
        }else if(jpf.getText().isEmpty())  
        {  
            JOptionPane.showMessageDialog(null,"���������룡","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);  
        }else  
        {  
            JOptionPane.showMessageDialog(null,"�û��������������\n����������","��ʾ��Ϣ",JOptionPane.ERROR_MESSAGE);  
            //��������  
            this.clear();  
        }  
    }  
  
          
}  