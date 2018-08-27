package com.xy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.xy.dao.AddressDao;
import com.xy.dao.UserDao;
import com.xy.po.Adress;
import com.xy.po.User;

@WebServlet("/ChAddressServlet")
public class ChAddressServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String province=req.getParameter("province");
		String city=req.getParameter("city");
		String detail=req.getParameter("detail");
		String zip=req.getParameter("zip");
		
		System.out.println(province);
		System.out.println(city);
		System.out.println(detail);
		System.out.println(zip);
		AddressDao aDao=new AddressDao();
		//UserDao uDao=new UserDao();
		HttpSession se=req.getSession();
		
		User user=(User)se.getAttribute("user");
		req.setAttribute("id",user.getId());
		int st=user.getId();
		System.out.println(st);
		//User user1=uDao.findUserById(user.getId());
		Adress adress=aDao.findByUid(user.getId());
		
		System.out.println(adress);
		if (adress!=null) {
			//User u=new User(username, newpassword);
			Adress  a=new Adress(province, city, detail, zip);
			System.out.println(a);
			aDao.updateAddres(a,st);
			
			req.setAttribute("user", user);
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			
		}else {
			req.getRequestDispatcher("404.jsp").forward(req, resp);
			
		}
	
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		this.doGet(req, resp);
	}

}
