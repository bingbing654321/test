package com.tsinghua;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Servlet implementation class Wel
 */
@WebServlet("/Wel")
public class Wel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Wel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Connection ct=null;
		
		
		response.setContentType("text/html;charset=gbk");
		//得到session
		HttpSession hs=request.getSession(true);
		String myName=(String)hs.getAttribute("uname");
		//判断
		String name="";
		String passwd="";
		if(myName==null) {
			//如果session中没有用户信息，再看看有没有cookie信息
			//从客户端得到所有cookie信息
			Cookie [] allCookies=request.getCookies();
			int i=0;
			//如果allCookie不为空
			if(allCookies!=null) {
				//从中取出cookie
				for(i=0;i<allCookies.length;i++) {
					//依次取出
					Cookie temp=allCookies[i];
					if(temp.getName().equals("myname")) {
						//得到cookie的值
						name=temp.getValue();
					}else if(temp.getName().equals("mypasswd")){
						passwd=temp.getValue();
					}
				}
				if(!name.equals("")&&!passwd.equals("")) {
					//到logincl去验证
					response.sendRedirect("logincl?username="+name+"&passwd="+passwd);
					return;
				}
			}
			
			
			//非法登陆
			response.sendRedirect("login?info=error1");
			return ;             
		}
		
		//解决中文乱码
		response.setCharacterEncoding("gbk");
		PrintWriter pw=response.getWriter();
		
		
		pw.println("<body bgcolor=#CED3FF>");
		
		pw.println("<img src=imgs/timg.gif width=300px height=90px><center>欢迎您："+myName+"<img src=imgs/me.gif width=30px height=20px><hr>");
		
		pw.println("<h1>管理用户</h1>");
		//做个超链接
		pw.println("<br><a href=login>返回重新登陆</a>");
		
		//添加网页访问次数的功能
		
		//pw.println("wel,hello,~~~~~~~~~~~~~~~"+u+"  uPass="+p);
		//=====================分页功能=========================
		int pageSize=3;//一页显示几条记录
		int pageNow=1;//希望显示第几页
		
		//动态接受pageNow
		String sPageNow=request.getParameter("pageNowok");
		if(sPageNow!=null) {
			pageNow=Integer.parseInt(sPageNow);
		}
		
		
		//得到rowCount
		try {
			
			//调用UserBeanCl
			UserBeanCl ubc=new UserBeanCl();
			ArrayList al=ubc.getResultByPage(pageNow,pageSize);
			
			
			pw.println("<table border=1>");
			pw.println("<tr bgcolor=pink><th>id</th><th>name</th><th>passwd</th><th>mail</th><th>grade</th><th>修改用户</th><th>删除用户</th></tr>");
			
			//定义一个颜色数组
			String [] mycol= {"silver","pink"};
			
			for(int i=0;i<al.size();i++) {
				UserBean ub=(UserBean)al.get(i);
				pw.println("<tr bgcolor="+mycol[i%2]+">");
				pw.println("<td>"+ub.getUserId()+"</td>");
				pw.println("<td>"+ub.getUserName()+"</td>");
				pw.println("<td>"+ub.getPasswd()+"</td>");
				pw.println("<td>"+ub.getMail()+"</td>");
				pw.println("<td>"+ub.getGrade()+"</td>");
				pw.println("<td><a href=update?uId="+ub.getUserId()+"&uName="+ub.getUserName()+"&uPass="+ub.getPasswd()+"&uMail="+ub.getMail()+"&uGrade="+ub.getGrade()+">修改用户</a></td>");
				pw.println("<td><a href=delusercl?userid="+ub.getUserId()+" onclick=\"return window.confirm('您确认要删除该用户吗？')\">删除用户</a></td>");
				pw.println("</tr>");
			}
			pw.println("</table>");
			//上一页
			if(pageNow!=1)
			pw.println("<a href=wel?pageNowok="+(pageNow-1)+">上一页</a>");
			//显示超链接-页数
			for(int i=pageNow;i<=pageNow+4;i++) {
				pw.println("<a href=wel?pageNowok="+i+">"+i+"</a>");
			}
			//下一页
			int pageCount=ubc.getPageCount();
			if(pageNow!=pageCount)
			pw.println("<a href=wel?pageNowok="+(pageNow+1)+">下一页</a><br>");
			pw.println("该网页被访问了"+this.getServletContext().getAttribute("visitTimes").toString()+"次"+"<br>");
			
			//指定跳转到某页
			//这里实际是一个表单
			pw.println("<form action=wel>");
			pw.println("<input type=text name=pageNowok>");
			pw.println("<input type=submit value=go>");
			pw.println("</form>");
			
			
			pw.println("您的ip="+request.getRemoteAddr()+"<br>");
			pw.println("您的机器名="+request.getRemoteHost()+"<br>");
			//pw.println("</center><hr><img src=imgs/sello.gif width=300px height=90px>");
			pw.println("</center><hr><img src=imgs/sello.gif width=300px height=90px>");
			
			pw.println("</body>");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//String u=request.getParameter("uname");
		//String p=request.getParameter("uPass");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
