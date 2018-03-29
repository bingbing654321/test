package com.tsinghua;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
			Class.forName("com.mysql.jdbc.Driver");
			ct=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/spdb","root","123456");
			//����statememt
			sm=ct.createStatement();
			rs=sm.executeQuery("select passwd from users where username='"+u
										+"'");
			if(rs.next()) {
				//˵���û��Ǵ��ڵ�
				String dbPasswd=rs.getString(1);
				if(dbPasswd.equals(p)) {
					//�Ϸ�
					//����֤�ɹ�����Ϣд��session
					HttpSession hs=request.getSession(true);
					//�޸�session�Ĵ���ʱ��
					hs.setMaxInactiveInterval(30);
					
					hs.setAttribute("uname", u);
					
					
					response.sendRedirect("wel��uname="+u+"&uPass="+p);
					
				}
			}else {
				//˵���û�������
				//���Ϸ�
				response.sendRedirect("login");
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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
