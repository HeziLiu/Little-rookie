package com.xy.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.dao.LoginDao;
import com.xy.dao.MD5Dao;
import com.xy.dao.RegisterDao;
import com.xy.dao.UserDao;
import com.xy.po.User;


@WebServlet("/registerservlet")
public class registerservlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean r;
		req.setCharacterEncoding("utf-8");
		String name=req.getParameter("uname").trim();
		String pwd=req.getParameter("upwd").trim();
		MD5Dao md5Dao=new MD5Dao();
		try {
			String mdpwd=md5Dao.EncoderByMd5(pwd);
			String email=req.getParameter("uemail");
			RegisterDao dao=new RegisterDao();
			UserDao userDao = new UserDao();
			HttpSession session=req.getSession();
			System.out.println(session.getAttribute("username"));
			User u =new User(name,mdpwd,email);  //
			//
		//	r=dao.Compare(name, mdpwd, email);  //
			r=dao.Comparename(name);		
			if(r)  
			{
				
				//req.setAttribute("user1", user1);
				req.setAttribute("register Error","The name is existing,Please enter other name!");          // 设置错误属性
				req.getRequestDispatcher("login.jsp").forward(req, resp);
				System.out.println("exist");
			}
			else{   
				
				dao.addOne(u);	
				dao.addCartForNewUser(userDao.findUserByNameAndPassword(name, mdpwd).getId());
				System.out.println("not exist and add to SQL ");
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
