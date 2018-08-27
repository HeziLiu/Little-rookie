package com.xy.dao;

import com.xy.po.Cart;
import com.xy.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CartDao {
	public int getCartCount(){
        Connection conn = DBUtils.getConnection();
        String sql = "select count(*) from cart";
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
	
	public boolean addCart(int user_id) {
		Connection conn = DBUtils.getConnection();
        String sql = "insert into cart values(?, ?)";
        PreparedStatement ps = null;
        try{
            // 设置参数
            ps = conn.prepareStatement(sql);
            ps.setInt(1, this.getCartCount() + 1);
            ps.setInt(2, user_id);
            // 执行
            if(ps.executeUpdate() > 0){
                System.out.println("插入cart表成功");
                return true;
            }
           // conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(null, ps, conn);
        }
        System.out.println("插入cart表失败！");
        return false;
	}
	
	
    public Cart findCartByCart_id(int cart_id){
        Connection conn = DBUtils.getConnection();
        String sql = "select * from cart where cart_id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cart_id);
            rs = ps.executeQuery();
            if(rs.next()){
                return new Cart(rs.getInt("cart_id"), rs.getInt("user_id"));
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(rs, ps, conn);
        }
        return null;
    }

    public static void main(String[] args){
        CartDao cartDao = new CartDao();
        System.out.println(cartDao.findCartByCart_id(1));
        System.out.println(cartDao.addCart(9));
    }
}
