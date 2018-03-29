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
		//接受用户名和密码
		String u=request.getParameter("username");
		String p=request.getParameter("passwd");
		
		Connection ct=null;
		Statement sm=null;
		ResultSet rs=null;
		//连接数据库
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ct=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/spdb","root","123456");
			//创建statememt
			sm=ct.createStatement();
			rs=sm.executeQuery("select passwd from users where username='"+u
										+"'");
			if(rs.next()) {
				//说明用户是存在的
				String dbPasswd=rs.getString(1);
				if(dbPasswd.equals(p)) {
					//合法
					//将验证成功的信息写入session
					HttpSession hs=request.getSession(true);
					//修改session的存在时间
					hs.setMaxInactiveInterval(30);
					
					hs.setAttribute("uname", u);
					
					
					response.sendRedirect("wel？uname="+u+"&uPass="+p);
					
				}
			}else {
				//说明用户不存在
				//不合法
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
