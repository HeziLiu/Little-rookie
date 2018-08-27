package com.xy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.dao.LoginDao;
import com.xy.dao.UserDao;
import com.xy.po.User;
@WebServlet("/loginservlet")
public class loginservlet extends HttpServlet{
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.setCharacterEncoding("utf-8");
	String name=req.getParameter("username");
	String pwd=req.getParameter("upwd");
	UserDao dao = new UserDao();
	User user=dao.findUserByNameAndPassword(name, pwd);	
	if(user!=null){
		System.out.println(user.toString() + "登录");
		HttpSession session=req.getSession();
		session.setAttribute("userid", user.getId());
		session.setAttribute("username", user.getUsername());
		session.setAttribute("user", user);
		req.setAttribute("username", user.getUsername());
	
		req.getRequestDispatcher("/judgeservlet").forward(req, resp);
		System.out.println("Login success");
		
	}
	else{
		System.out.println("Login failed");
		req.getRequestDispatcher("login.jsp").forward(req, resp);
		
	}
}
@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.setCharacterEncoding("utf-8");
	this.doGet(req, resp);
	}
}
