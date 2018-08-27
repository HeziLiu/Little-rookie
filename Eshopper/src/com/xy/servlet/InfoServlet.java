package com.xy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.dao.LoginDao;
import com.xy.dao.UserDao;
import com.xy.po.User;




@WebServlet("/InfoServlet")
public class InfoServlet extends HttpServlet{
  @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 
	
	  
			UserDao dao=new UserDao();
			HttpSession se=req.getSession();
			User user=(User)se.getAttribute("user");
		User user1=dao.findUserById(user.getId());
			if(user1!=null)
			{
				//"username",user.getUsername()req.setAttribute("username",user.getUsername());"user", user
				req.setAttribute("user", user);
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("username","no login");
				
				
			}
			req.getRequestDispatcher("password.jsp").forward(req, resp);
			
  }
  @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	  req.setCharacterEncoding("utf-8");
		this.doGet(req, resp);
	}

}
