package com.xy.servlet;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.dao.CategoryDao;
import com.xy.dao.ProductDao;
import com.xy.po.Category;
import com.xy.po.Product;

@WebServlet("/ProductListTypeServlet")
public class ProductListTypeServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categotyid = req.getParameter("typeid");
		System.out.println(categotyid);
		
		ProductDao pdao = new ProductDao();
		CategoryDao cDao = new CategoryDao();
		//query product list
		List<Product>list = null;
	    if(categotyid != null && !categotyid.equals("")) {
			list = pdao.findBycategory(Integer.parseInt(categotyid));
		}else {
			list = pdao.findAll();
			System.out.println(22);
		}
		//query product category
		List<Category> types = cDao.findAll();
		System.out.println(types);
		req.setAttribute("list", list);
		req.setAttribute("types", types);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		this.doGet(req, resp);
	}
}
