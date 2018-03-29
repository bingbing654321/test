package com.tsinghua;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		if(myName==null) {
			//�Ƿ���½
			response.sendRedirect("login?info=error1");
			return ;             
		}
		
		//=====================��ҳ����=========================
		int pageSize=3;//һҳ��ʾ������¼
		int pageNow=1;//ϣ����ʾ�ڼ�ҳ
		int rowCount=0;//���м�����¼�����
		int pageCount=0;//���м�ҳ�����㣩
		//�õ�rowCount
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ct=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/spdb","root","123456");
			PreparedStatement ps = ct.prepareStatement("select count(*) from users");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				rowCount=rs.getInt(1);
			}
			//����pageCount
			if(pageCount%pageSize==0) {
				pageCount=rowCount/pageSize;
			}else {
				pageCount=rowCount/pageSize+1;
			}
			//***********************************
			//���Ľ�25:46
			ps=ct.prepareStatement("");
			//************************************
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//String u=request.getParameter("uname");
		//String p=request.getParameter("uPass");
		
		PrintWriter pw=response.getWriter();
		
		//��servlet����ʾͼƬ
		pw.println("<img src=imgs/timg.gif><br>");
		
		pw.println("wel,hello<br>");
		pw.println("��� �û��� �ǣ�"+myName);
		//����������
		pw.println("<br><a href=login>�������µ�½</a>");
		//pw.println("wel,hello,~~~~~~~~~~~~~~~"+u+"  uPass="+p);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
