package com.xy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;
import com.xy.dao.Cart_itemDao;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("user_id") == "") {
			request.getRequestDispatcher("/loginservlet").forward(request, response);
			return;
		}
		int user_id = Integer.parseInt(request.getParameter("user_id"));	
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		System.out.println("user_id: " + user_id);
		System.out.println("product_id: " + product_id);
		Cart_itemDao cart_itemDao = new Cart_itemDao();
		int count = cart_itemDao.getCount(user_id, product_id);
		if(count == 0) {
			cart_itemDao.insertIntoCart_item(user_id, product_id, 1);
		}
		else {
			cart_itemDao.insertIntoCart_item(user_id, product_id, count + 1);
		}
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
