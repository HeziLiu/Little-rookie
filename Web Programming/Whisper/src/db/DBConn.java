package db;

import java.sql.*;


public class DBConn {
	public Connection getConn(){
		Connection conn=null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url="jdbc:mysql://localhost:3307/whisper?user=root&password=962464&useUnicode=true&characterEncoding=UTF-8";
			conn=DriverManager.getConnection(url);
			return conn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	public boolean checkUsername(String uname){//检查用户名是否存在
		boolean b=true;
		try{
			Connection conn=getConn();
			Statement stmt=conn.createStatement();
			String sql="select id from member where username='"+uname+"'";
			ResultSet rs=stmt.executeQuery(sql);
			if(!rs.next()) b=false;
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return b;
	}
	
	public boolean checkPassword(String uname,String pass){//检查密码是否正确
		boolean b=false;
		try{
			Connection conn=getConn();
			Statement stmt=conn.createStatement();
			String sql="select id from member where username='"+uname+"'and password='"+pass+"'";
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()) b=true;
			rs.close();stmt.close();conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return b;
	}
	
	public int exeSQL(String sql){//执行更新语句
		int i=0;
		try{
			Connection conn=getConn();
			Statement stmt=conn.createStatement();
			i=stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return i;
	}
	
	public Member getInfo(String uname){
		Member m=null;
		try{
			Connection conn=getConn();
			Statement stmt=conn.createStatement();
			String sql="select * from member where username='"+uname+"'";
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				m=new Member();
				m.setId(rs.getInt("id"));
				m.setUsername(rs.getString("username"));
				m.setPassword(rs.getString("password"));
				m.setSex(rs.getString("sex"));
				m.setGrade(rs.getString("grade"));
				m.setTel(rs.getString("tel"));
				m.setComment(rs.getString("comment"));
				m.setInterest(rs.getString("interest"));
			}
			rs.close();stmt.close();conn.close();
		}catch(Exception e){e.printStackTrace();}
		return m;
	}
}
