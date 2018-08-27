package com.xy.dao;

import com.xy.po.User;
import com.xy.utils.DBUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDao {
	

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
     * 插入新的用户到user表中
     * @param user  user
     * @return  是否插入成功
     */
    public boolean addUser(User user){
        Connection conn = DBUtils.getConnection();
        String sql = "insert into user values(?, ?, ?, ?)";
        PreparedStatement ps = null;
        try{
            // 设置参数
            ps = conn.prepareStatement(sql);
            ps.setInt(1, new UserDao().getUserCount() + 1);
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
         
            // 执行
            if(ps.executeUpdate() > 0){
                System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 插入" + user.toString() + ": 成功!");
                return true;
            }
           // conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(null, ps, conn);
        }

        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 插入" + user.toString() + ": 失败！");
        return false;
    }

    /**
     * 通过id删除指定用户
     * @param id    待删除用户的id
     * @return      是否删除成功
     */
    public boolean deleteUser(int id){
        Connection conn = DBUtils.getConnection();
        String sql = "delete from user where user_id = ?";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 删除id = " + id + "的用户: 成功！");
                return true;
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(null, ps, conn);
        }
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 删除id = " + id + "的用户: 失败！");
        return false;
    }

    /**
     * 通过id修改用户信息
     * @param user  待修改用户
     * @return      是否修改成功
     */
    public boolean updateOne(String username, User u) {
		Connection conn = DBUtils.getConnection();
		String sql = "update user set password=? where username=?";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, u.getPassword());
			
			ps.setString(2, username);
			//ִ��
			
			int row = ps.executeUpdate();
			if(row > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection(null, ps, conn);
		}
		return false;
	}
    public boolean modifyUser(User user){
        Connection conn = DBUtils.getConnection();
        String sql = "update user set username = ?, email = ?, password = ?,telephone=?,birthday=?,sex=? where user_id = ?";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getTelephone());
          
            ps.setString(6, user.getSex());
            if(ps.executeUpdate() > 0){
                System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 修改用户 " + user.toString() + " 成功！");
                return true;
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(null, ps, conn);
        }
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 修改用户 " + user.toString() +  "失败！");
        return false;
    }
    public boolean updateUser(int user_id,User user){
        Connection conn = DBUtils.getConnection();
        
        String sql = "update user set username = ?, email = ?,telephone=?,birthday=?,sex=? where user_id = ?";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
          
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getTelephone());
            ps.setString(4, user.getSex());
            ps.setString(5, user.getBirthday());
            ps.setInt(6,user_id);
            int row = ps.executeUpdate();
			if(row > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection(null, ps, conn);
		}
		return false;
    }

    /**
     * 根据用户id查找用户
     * @param id    查询id
     * @return      和该id相关联的用户
     */
    public User findUserById(int id){
        Connection conn = DBUtils.getConnection();
        String sql = "select * from user where user_id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                return new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(rs, ps, conn);
        }
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 查找 id为：" + id + "的用户失败！");
        return null;
    }
    public User findUserByName(String username) {
    	Connection connection=DBUtils.getConnection();
    	String sql="select * from user where user_id = ?";
    	PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                return new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(rs, ps, connection);
        }
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 查找 id为：" + username + "的用户失败！");
        return null;
    }

    /**
     * 查找user表所有内容
     * @return  user表的所有内容
     */
    public List<User> findAllUser(){
        List<User> userlist = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from user";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                userlist.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(rs, ps, conn);
        }
        return userlist;
    }

    /**
     * 根据用户名和密码查找用户
     * @param username  用户名
     * @param password  密码
     * @return          和用户名和密码匹配的用户
     */
    public User findUserByNameAndPassword(String username, String password){
        Connection conn = DBUtils.getConnection();
        String sql = "select * from user where username = ? and password = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(rs, ps, conn);
        }
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 用户：" + username + "使用密码：" + password + "登录");
        return null;
    }
    
    /**
     * 2根据用户名和密码查找用户
     * @param username  用户名
     * @param password  密码
     * @return          和用户名和密码匹配的用户
     */
    public List<User> findUserByNAPwd(String username, String password){
    	List<User> userlist = new ArrayList<>();
    	Connection conn = DBUtils.getConnection();
        String sql = "select * from user where username = ? and password = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
            	userlist.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(rs, ps, conn);
        }
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ": 用户：" + username + "使用密码：" + password + "登录");
        return userlist;
    }

    public static void main(String[] args){
        UserDao userdao = new UserDao();
       /* System.out.println(userdao.findUserById(3).toString());
        System.out.println(userdao.findAllUser());
        System.out.println(userdao.findUserByNameAndPassword("nicholas", "nicholas"));
        System.out.println(userdao.getUserCount());*/
        //userdao.updateOne("Dinging47", new User("eee","111111"));
        userdao.updateUser(2,new User("yyy","yyy","yyy","uu","uu"));
        System.out.println(userdao.findUserByNameAndPassword("Leo", "stunning"));
    }
}
