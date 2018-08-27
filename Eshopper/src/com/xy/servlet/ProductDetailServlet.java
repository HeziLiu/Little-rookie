package com.xy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.dao.ProductDao;
import com.xy.po.Product;

@WebServlet("/ProductDetailServlet")
public class ProductDetailServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String productid = req.getParameter("productid");
		productid = productid.substring(0);
		
		//����dao����
		ProductDao pDao = new ProductDao();
		//����product_id��ѯ
		Product product = pDao.findProductById(Integer.parseInt(productid));
		if(product != null) {
			HttpSession session = req.getSession();
			session.setAttribute("product", product);
			
			req.getRequestDispatcher("product-details.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		this.doGet(req, resp);
	}
}
