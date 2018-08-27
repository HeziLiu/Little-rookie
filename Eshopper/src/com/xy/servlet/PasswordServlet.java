package com.xy.servlet;

import java.io.IOException;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.dao.UserDao;
import com.xy.po.User;

@WebServlet("/PasswordServlet")
public class PasswordServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username=req.getParameter("username");
		String oldpassword=req.getParameter("oldpassword");
		String newpassword=req.getParameter("newpassword");
		String firmpassword=req.getParameter("firmpassword");
		UserDao dao=new UserDao();
		System.out.println(username);
		System.out.print(oldpassword);
		System.out.println(newpassword);
		//User user1=dao.findUserByName(username);
		HttpSession se=req.getSession();
		User user=(User)se.getAttribute("user");
	   User user1=dao.findUserById(user.getId());
		if (user1!=null&&Integer.parseInt(newpassword)==Integer.parseInt(firmpassword)) {
			User u=new User(username, newpassword);
			req.setAttribute("id",user.getId());		
			dao.updateOne(username, u);
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
