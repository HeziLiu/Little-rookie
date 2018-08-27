package com.xy.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Formatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.xy.dao.*;
import com.xy.po.User;


import com.xy.po.Adress;

@WebServlet("/ChInfoServlet")
public class ChInfoServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AddressDao aDao=new AddressDao();
		UserDao uDao=new UserDao();
		String username=req.getParameter("username");
		String email=req.getParameter("email");
		String telephone=req.getParameter("telephone");
		String birthday=req.getParameter("birthday");
		
		String sex=req.getParameter("sex");
		UserDao dao=new UserDao();
	//	System.out.println(username);
		System.out.print(email);
		System.out.println(telephone);
		System.out.print(birthday);
		System.out.println(sex);
		HttpSession se=req.getSession();
		User user=(User)se.getAttribute("user");
	   User user1=dao.findUserById(user.getId());
	   System.out.println(user1);
		//req.setAttribute("user", user);
		//System.out.println(user);
		int St=user.getId();
		System.out.println(St);
	   if (user1!=null) {
			User u=new User(username, telephone, email, birthday, sex);
			req.setAttribute("id",user.getId());		
			dao.updateUser(St,u);
			/*req.setAttribute("id",user.getId());		
			dao.updateOne(username, u);*/
			req.setAttribute("user", user);
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			
		}
		else {
			req.getRequestDispatcher("404.jsp").forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		this.doGet(req, resp);
	}
}


