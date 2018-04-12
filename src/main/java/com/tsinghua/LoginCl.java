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
    //重写init函数
    public void init() {
    	try {
    		//只会被调用一次
    		
    		FileReader f=new FileReader("F:\\a.txt");
			BufferedReader bw=new BufferedReader(f);
			//读出一行数据
			String numVal=bw.readLine();
			bw.close();
			//将times值放入servletcontext
    		getServletContext().setAttribute("visitTimes", numVal);
			
    		System.out.println("init 被调用");
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    //重写destroy
    public void destroy() {
    	try {
    		
    		//将新的次数写回文件
			FileWriter ff=new FileWriter("F:\\a.txt");
			BufferedWriter bww=new BufferedWriter(ff);
			bww.write(this.getServletContext().getAttribute("visitTimes").toString());
			bww.close();
    		
    		System.out.println("destroy 被调用***********************************************************");
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    }
    
    
    
    
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
			//调用UserBeanCl
			UserBeanCl ubc=new UserBeanCl();
			
			if(ubc.checkUser(u,p)) {
				
					//合法
					
					String keep=request.getParameter("keep");
					if(keep!=null) {
						//将用户名和密码保存在客户端（cookie）
						//创建
						Cookie name=new Cookie("myname",u);
						Cookie pass=new Cookie("mypasswd",p);
						//设置时间
						name.setMaxAge(14*24*3600);
						pass.setMaxAge(14*24*3600);
						//回写到客户端
						response.addCookie(name);
						response.addCookie(pass);
					}
					
					
					//将验证成功的信息写入session
					HttpSession hs=request.getSession(true);
					//修改session的存在时间
					hs.setMaxInactiveInterval(30);
					
					hs.setAttribute("uname", u);
					
					//将servletContext中的visitTime对应的值++
					String times=this.getServletContext().getAttribute("visitTimes").toString();
					//对times++再重新放回servlet
					this.getServletContext().setAttribute("visitTimes", (Integer.parseInt(times)+1)+"");
					
					
					response.sendRedirect("main");
					
				
			}else {
				//说明用户不存在
				//不合法
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
