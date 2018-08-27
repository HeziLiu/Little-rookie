package com.xy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.org.apache.bcel.internal.generic.DALOAD;
import com.xy.po.User;
import com.xy.utils.DBUtils;



public class RegisterDao {
	public static void main(String[] args)
	{
	RegisterDao dao=new RegisterDao();
	//	User u =new User("fd","2323","449315760@qq.com");
	//	dao.addOne(u);	
	//	System.out.println(dao.addOne(u));
	//	dao.updateOne(4, new User("jinlin","1100"));
	//	User user=dao.findById(2);
	//	System.out.println(user.toString());
	//	System.out.println(dao.findAll());
		System.out.println(dao.addCartForNewUser(14));
		
	}
/*
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
			return new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));		
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		DBUtils.closeConnection(rs, ps, conn);	
	}	
	System.out.println("success");
	return null;
	
}*/
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
			return new User(rs.getString(2),rs.getString(3),rs.getString(4));			
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		DBUtils.closeConnection(rs,ps, conn);		
	}	
	System.out.println("success");
	return null;
}
public boolean Compare(String name,String pwd,String email) {
	boolean r=false;
	Connection conn=DBUtils.getConnection();
	String sql="select * from user where username=? and password=? and email=?";
	PreparedStatement ps=null;
	ResultSet rs=null;
	try {
		ps=conn.prepareStatement(sql);
		ps.setString(1, name );
		ps.setString(2, pwd );
		ps.setString(3, email );
		rs=ps.executeQuery();
		if(rs.next())
		{
			r=true;			
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		DBUtils.closeConnection(rs,ps, conn);		
	}
	return r;		
}
public boolean addOne(User u)
{
	boolean result =false;
	Connection conn=DBUtils.getConnection();
	PreparedStatement ps=null;
	String sql="INSERT into user(username,password,email) VALUES(?,?,?)";
	try {
		ps=conn.prepareStatement(sql);
		ps.setString(1,u.getUsername());
		ps.setString(2,u.getPassword());
		ps.setString(3,u.getEmail());
		//执锟斤拷
		int row=ps.executeUpdate();
		if(row>0) {	
			result=true;
		}	
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		DBUtils.closeConnection (null, ps, conn);		
	}	
	
	return result;
}	


 public int getUserCount(){
        Connection conn = DBUtils.getConnection();
        String sql = "select count(*) from user";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowCount = 0;
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            rowCount = rs.getInt(1);
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(rs, ps, conn);
        }
        return rowCount;
    }

/**
 * create a cart for new user
 */
public boolean addCartForNewUser(int user_id)
{
	//int userCount = getUserCount();
	Connection conn=DBUtils.getConnection();
	PreparedStatement ps=null;
	String sql="INSERT into cart VALUES(?, ?)";
	try {
		ps=conn.prepareStatement(sql);
		ps.setInt(1, user_id);
		ps.setInt(2, user_id);
		int row=ps.executeUpdate();
		if(row>0) {	
			System.out.println("cteate cart for " + user_id + " successful!");
			return true;
			
		}	
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		DBUtils.closeConnection (null, ps, conn);		
	}	
	System.out.println("create cart for " + user_id + " failed!");
	return false;
}	

public boolean Comparename(String name) {
	boolean r=false;
	Connection conn=DBUtils.getConnection();
	String sql="select * from user where username=?";
	PreparedStatement ps=null;
	ResultSet rs=null;
	try {
		ps=conn.prepareStatement(sql);
		ps.setString(1, name );
		rs=ps.executeQuery();
		if(rs.next())
		{
			r=true;			
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		DBUtils.closeConnection(rs,ps, conn);		
	}
	return r;		
}
}
