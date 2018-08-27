package com.xy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xy.po.User;
import com.xy.utils.DBUtils;

public class LoginDao {
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		LoginDao dao=new LoginDao();
	//	User u =new User("fd","2323");
	//	dao.addOne(u);	
	//	System.out.println(dao.delOne(3));
	//	dao.updateOne(4, new User("jinlin","1100"));
	//	User user=dao.findById(2);
	//	System.out.println(user.toString());
	//	System.out.println(dao.findAll());
		dao.findUserByNameAndPassword("Leo", "stunning");
	}


public User findById(int id) {
	Connection conn=DBUtils.getConnection();
	String sql="select * from user where user_id=?";
	PreparedStatement ps=null;
	ResultSet rs=null;
	try {
		ps=conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs=ps.executeQuery();
		if(rs.next())
		{
			return new User(rs.getString(2),rs.getString(3),rs.getString(4));		
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		DBUtils.closeConnection(rs, ps, conn);	
	}	
	System.out.println("success");
	return null;
	
}
/**
 * @param name
 * @param pwd
 * @return
 */
public User findUserByNameAndPassword(String name, String pwd) {
	Connection conn=DBUtils.getConnection();
	String sql="select * from user where username=? and password=?";
	PreparedStatement ps=null;
	ResultSet rs=null;
	try {
		ps=conn.prepareStatement(sql);
		ps.setString(1, name );
		ps.setString(2, pwd );
		rs=ps.executeQuery();
		if(rs.next())
		{
			System.out.println("find it");
			System.out.print(rs);
			return new User(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4));			
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		DBUtils.closeConnection(rs,ps, conn);		
	}	
	System.out.println("success");
	return null;
}

}
