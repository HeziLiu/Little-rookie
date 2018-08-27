package com.xy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;

import com.xy.po.Product;
import com.xy.utils.DBUtils;

public class ProductDao {
	
	public Product findProductById(int id) {
		Connection conn = DBUtils.getConnection();
		String sql = "select * from product where product_id=?";
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			resultSet = ps.executeQuery();
			if(resultSet.next()) {
				return new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection(resultSet, ps, conn);
		}
		
		return null;
	}

	public List<Product> findBycategory(int category_id) {
		List<Product>list = new ArrayList<>();
		Connection conn = DBUtils.getConnection();
		String sql = "select * from product where category_id=?";
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, category_id);
			resultSet = ps.executeQuery();
			while(resultSet.next()) {
				list.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection(resultSet, ps, conn);
		}
		
		return list;
	}
	
	public List<Product>findAll(){
		List<Product>list = new ArrayList<>();
		Connection conn = DBUtils.getConnection();
		String sql = "select * from product";
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
			while(resultSet.next()) {
				list.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection(resultSet, ps, conn);
		}
		
		return list;
	}
}
