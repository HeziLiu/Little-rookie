package com.xy.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xy.dao.UserDao;
import com.xy.po.User;

import java.io.FileWriter;
import java.io.File;
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("执行到了changePassword函数");
		PrintWriter pw = resp.getWriter();
		//User user =  (User) req.getSession().getAttribute("user");
		User user=(User)req.getSession().getAttribute("user");
		//通过用户id重新查找用户
		UserDao
		 dao=new UserDao();
		User userNow=dao.findUserById(user.getId());
		


	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	


}
