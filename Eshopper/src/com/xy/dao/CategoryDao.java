package com.xy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.ls.LSInput;

import com.xy.po.Category;
import com.xy.utils.DBUtils;

public class CategoryDao {

	public List<Category> findAll() {
		List<Category>list = new ArrayList<>();
		Connection conn = DBUtils.getConnection();
		String sql = "select * from category";
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
			while(resultSet.next()) {
				list.add(new Category(resultSet.getInt(1), resultSet.getString(2)));
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
