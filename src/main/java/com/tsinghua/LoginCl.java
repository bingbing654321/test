package com.tsinghua;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginCl
 */
@WebServlet("/LoginCl")
public class LoginCl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //��дinit����
    public void init() {
    	try {
    		//ֻ�ᱻ����һ��
    		
    		FileReader f=new FileReader("F:\\a.txt");
			BufferedReader bw=new BufferedReader(f);
			//����һ������
			String numVal=bw.readLine();
			bw.close();
			//��timesֵ����servletcontext
    		getServletContext().setAttribute("visitTimes", numVal);
			
    		System.out.println("init ������");
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    //��дdestroy
    public void destroy() {
    	try {
    		
    		//���µĴ���д���ļ�
			FileWriter ff=new FileWriter("F:\\a.txt");
			BufferedWriter bww=new BufferedWriter(ff);
			bww.write(this.getServletContext().getAttribute("visitTimes").toString());
			bww.close();
    		
    		System.out.println("destroy ������***********************************************************");
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    }
    
    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//�����û���������
		String u=request.getParameter("username");
		String p=request.getParameter("passwd");
		
		Connection ct=null;
		Statement sm=null;
		ResultSet rs=null;
		//�������ݿ�
		try {
			//����UserBeanCl
			UserBeanCl ubc=new UserBeanCl();
			
			if(ubc.checkUser(u,p)) {
				
					//�Ϸ�
					
					String keep=request.getParameter("keep");
					if(keep!=null) {
						//���û��������뱣���ڿͻ��ˣ�cookie��
						//����
						Cookie name=new Cookie("myname",u);
						Cookie pass=new Cookie("mypasswd",p);
						//����ʱ��
						name.setMaxAge(14*24*3600);
						pass.setMaxAge(14*24*3600);
						//��д���ͻ���
						response.addCookie(name);
						response.addCookie(pass);
					}
					
					
					//����֤�ɹ�����Ϣд��session
					HttpSession hs=request.getSession(true);
					//�޸�session�Ĵ���ʱ��
					hs.setMaxInactiveInterval(30);
					
					hs.setAttribute("uname", u);
					
					//��servletContext�е�visitTime��Ӧ��ֵ++
					String times=this.getServletContext().getAttribute("visitTimes").toString();
					//��times++�����·Ż�servlet
					this.getServletContext().setAttribute("visitTimes", (Integer.parseInt(times)+1)+"");
					
					
					response.sendRedirect("main");
					
				
			}else {
				//˵���û�������
				//���Ϸ�
				response.sendRedirect("login");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				if(rs!=null) {
					rs.close();
				}
				if(sm!=null) {
					sm.close();
				}
				if(ct!=null) {
					ct.close();
				}
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
