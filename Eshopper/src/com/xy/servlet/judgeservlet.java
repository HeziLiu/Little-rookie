package com.xy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.po.User;
@WebServlet("/judgeservlet")
public class judgeservlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession se=req.getSession();
		User user=(User)se.getAttribute("user");
		if(user!=null)
		{
			System.out.println("got it: " + user.toString());
			//req.setAttribute("username",user.getUsername());
			req.getRequestDispatcher("/ProductListTypeServlet").forward(req, resp);
		}
		else
		{
			req.setAttribute("username","not login");	
			
		}
		req.getRequestDispatcher("/ProductListTypeServlet").forward(req, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		this.doGet(req, resp);
	}
	
	
}
