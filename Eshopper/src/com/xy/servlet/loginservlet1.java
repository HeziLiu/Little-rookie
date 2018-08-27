package com.xy.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.dao.LoginDao;
import com.xy.dao.MD5Dao;
import com.xy.po.User;
@WebServlet("/loginservlet1")
public class loginservlet1 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		LoginDao dao=new LoginDao();
		MD5Dao md5dao=new MD5Dao();
		String name=req.getParameter("username");
		String pwd=(req.getParameter("upassword"));
		try {
			String md5pwd=md5dao.EncoderByMd5(pwd);
			User user=dao.findUserByNameAndPassword(name,md5pwd);		
			if(user!=null)
			{
				HttpSession session=req.getSession();
				session.setAttribute("userid", user.getId());
				session.setAttribute("username", user.getUsername());
				session.setAttribute("user", user);
				req.setAttribute("username", user.getUsername());
			
				req.getRequestDispatcher("judgeservlet").forward(req, resp);
				
				
			}
			else{
				req.setAttribute("loginError","Can't Find User,Please Reenter Or Register!");          // …Ë÷√¥ÌŒÛ Ù–‘
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			
				
			}
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
			
		
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		this.doGet(req, resp);
	}

}
