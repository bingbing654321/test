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
		//�õ�session
		HttpSession hs=request.getSession(true);
		String myName=(String)hs.getAttribute("uname");
		//�ж�
		String name="";
		String passwd="";
		if(myName==null) {
			//���session��û���û���Ϣ���ٿ�����û��cookie��Ϣ
			//�ӿͻ��˵õ�����cookie��Ϣ
			Cookie [] allCookies=request.getCookies();
			int i=0;
			//���allCookie��Ϊ��
			if(allCookies!=null) {
				//����ȡ��cookie
				for(i=0;i<allCookies.length;i++) {
					//����ȡ��
					Cookie temp=allCookies[i];
					if(temp.getName().equals("myname")) {
						//�õ�cookie��ֵ
						name=temp.getValue();
					}else if(temp.getName().equals("mypasswd")){
						passwd=temp.getValue();
					}
				}
				if(!name.equals("")&&!passwd.equals("")) {
					//��loginclȥ��֤
					response.sendRedirect("logincl?username="+name+"&passwd="+passwd);
					return;
				}
			}
			
			
			//�Ƿ���½
			response.sendRedirect("login?info=error1");
			return ;             
		}
		
		//�����������
		response.setCharacterEncoding("gbk");
		PrintWriter pw=response.getWriter();
		
		
		pw.println("<body bgcolor=#CED3FF>");
		
		pw.println("<img src=imgs/timg.gif width=300px height=90px><center>��ӭ����"+myName+"<img src=imgs/me.gif width=30px height=20px><hr>");
		
		pw.println("<h1>�����û�</h1>");
		//����������
		pw.println("<br><a href=login>�������µ�½</a>");
		
		//�����ҳ���ʴ����Ĺ���
		
		//pw.println("wel,hello,~~~~~~~~~~~~~~~"+u+"  uPass="+p);
		//=====================��ҳ����=========================
		int pageSize=3;//һҳ��ʾ������¼
		int pageNow=1;//ϣ����ʾ�ڼ�ҳ
		
		//��̬����pageNow
		String sPageNow=request.getParameter("pageNowok");
		if(sPageNow!=null) {
			pageNow=Integer.parseInt(sPageNow);
		}
		
		
		//�õ�rowCount
		try {
			
			//����UserBeanCl
			UserBeanCl ubc=new UserBeanCl();
			ArrayList al=ubc.getResultByPage(pageNow,pageSize);
			
			
			pw.println("<table border=1>");
			pw.println("<tr bgcolor=pink><th>id</th><th>name</th><th>passwd</th><th>mail</th><th>grade</th><th>�޸��û�</th><th>ɾ���û�</th></tr>");
			
			//����һ����ɫ����
			String [] mycol= {"silver","pink"};
			
			for(int i=0;i<al.size();i++) {
				UserBean ub=(UserBean)al.get(i);
				pw.println("<tr bgcolor="+mycol[i%2]+">");
				pw.println("<td>"+ub.getUserId()+"</td>");
				pw.println("<td>"+ub.getUserName()+"</td>");
				pw.println("<td>"+ub.getPasswd()+"</td>");
				pw.println("<td>"+ub.getMail()+"</td>");
				pw.println("<td>"+ub.getGrade()+"</td>");
				pw.println("<td><a href=update?uId="+ub.getUserId()+"&uName="+ub.getUserName()+"&uPass="+ub.getPasswd()+"&uMail="+ub.getMail()+"&uGrade="+ub.getGrade()+">�޸��û�</a></td>");
				pw.println("<td><a href=delusercl?userid="+ub.getUserId()+" onclick=\"return window.confirm('��ȷ��Ҫɾ�����û���')\">ɾ���û�</a></td>");
				pw.println("</tr>");
			}
			pw.println("</table>");
			//��һҳ
			if(pageNow!=1)
			pw.println("<a href=wel?pageNowok="+(pageNow-1)+">��һҳ</a>");
			//��ʾ������-ҳ��
			for(int i=pageNow;i<=pageNow+4;i++) {
				pw.println("<a href=wel?pageNowok="+i+">"+i+"</a>");
			}
			//��һҳ
			int pageCount=ubc.getPageCount();
			if(pageNow!=pageCount)
			pw.println("<a href=wel?pageNowok="+(pageNow+1)+">��һҳ</a><br>");
			pw.println("����ҳ��������"+this.getServletContext().getAttribute("visitTimes").toString()+"��"+"<br>");
			
			//ָ����ת��ĳҳ
			//����ʵ����һ����
			pw.println("<form action=wel>");
			pw.println("<input type=text name=pageNowok>");
			pw.println("<input type=submit value=go>");
			pw.println("</form>");
			
			
			pw.println("����ip="+request.getRemoteAddr()+"<br>");
			pw.println("���Ļ�����="+request.getRemoteHost()+"<br>");
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
