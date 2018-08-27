package com.xy.dao;

import com.mysql.jdbc.PerConnectionLRUFactory;
import com.xy.po.Cart_item;
import com.xy.utils.DBUtils;
import com.xy.vo.CartVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cart_itemDao {
	// 返回当前cart_item表记录数量
	public int getCart_itemCount(){
        Connection conn = DBUtils.getConnection();
        String sql = "select count(*) from cart_item";
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
	 * 根据cart_item_id查找cart_item表
	 * @param cart_item_id	cart_item_id
	 * @return				一条记录
	 */
    public Cart_item findCart_itemByCart_item_id(int cart_item_id){
        Connection conn = DBUtils.getConnection();
        String sql = "select * from cart_item where cart_item_id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cart_item_id);
            rs = ps.executeQuery();
            if(rs.next()){
                return new Cart_item(rs.getInt("cart_item_id"), rs.getInt("cart_id"), rs.getInt("user_id"),
                        rs.getInt("product_id"), rs.getInt("product_quantity"));
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

    /**
     * 根据user_id查找cart_item表并展示改用户的购物车
     * @param user_id   user_id
     * @return          cart_item列表
     */
    public List<CartVo> findCartVoByUser_id(int user_id){
        Connection conn = DBUtils.getConnection();
        String sql = "SELECT product.product_id, product_name, stocks, price, description, product_quantity , " +
                "product_quantity * price as total " +
                "from product, cart_item " +
                "where cart_item.product_id = product.product_id and user_id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CartVo> list = new ArrayList<>();
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new CartVo(rs.getInt("product_id"), rs.getString("product_name"), rs.getString("description"),
                        rs.getDouble("price"), rs.getInt("product_quantity"), rs.getDouble("total")));
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(rs, ps, conn);
        }
        return list;
    }
    
    /**
     * 查找某个用户的购物车中某个商品的数量
     * @param user_id	user_id
     * @param product_id	product_id
     * @return		某个商品的数量
     */
    public int getCount(int user_id, int product_id) {
    	Connection conn = DBUtils.getConnection();
        String sql = "select count(*) from cart_item where user_id = ? and product_id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowCount = 0;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2,  product_id);
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
     * 根据user_id和product_id向cart_item表插入数据
     * @param user_id	user_id
     * @param product_id	cart_id
     * @param product_quantity	product_quantity
     * @return	是否插入成功
     */
    public boolean insertIntoCart_item(int user_id, int product_id, int product_quantity) {
    	Connection conn = DBUtils.getConnection();
        String sql = "insert into cart_item(cart_id, user_id, product_id, product_quantity, cart_item_id) values(?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, user_id);
            ps.setInt(3, product_id);
            ps.setInt(4, product_quantity);
            ps.setInt(5, getCart_itemCount() + 1);
            if(ps.executeUpdate() > 0){
            	System.out.println("成功写入cart_item表");
                return true;
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(null, ps, conn);
        }
        System.out.println("写入cart_item表失败");
        return false;
    }
    
    /**
     * 根据user_id和product_id更新cart_item表, 每次调用对应product_quantity加一或减一
     * @param user_id	
     * @param product_id
     * @param operation	加或减
     * @return	是否成功
     */
    public boolean updateCart_item(int user_id, int product_id, int operation) {
    	Connection conn = DBUtils.getConnection();
        String sql1 = "update cart_item set product_quantity = product_quantity - 1 where user_id = ? and product_id = ?";
        String sql2 = "update cart_item set product_quantity = product_quantity + 1 where user_id = ? and product_id = ?";
        PreparedStatement ps = null;
        try{
        	if(operation == -1) {
        		ps = conn.prepareStatement(sql1);
        	}
        	else {
        		ps = conn.prepareStatement(sql2);
        	}
            
            ps.setInt(1, user_id);
            ps.setInt(2, product_id);
            if(ps.executeUpdate() > 0){
            	System.out.println("成功更新cart_item表");
                return true;
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(null, ps, conn);
        }
        System.out.println("更新cart_item表失败");
        return false;
    }
    
    /**
     * 根据user_id和product_id从cart_item表删除记录
     * @param user_id	user_id
     * @param product_id	product_id
     * @return	是否删除成功
     */
    public boolean deleteCart_item(int user_id, int product_id) {
    	Connection conn = DBUtils.getConnection();
        String sql = "delete from cart_item where user_id = ? and product_id = ?";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, product_id);
            if(ps.executeUpdate() > 0){
            	System.out.println("成功从cart_item表删除一条记录");
                return true;
            }
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(null, ps, conn);
        }
        System.out.println("删除一条cart_item记录失败");
        return false;
    }

    public static void main(String[] args){
        Cart_itemDao cart_itemDao = new Cart_itemDao();
        //System.out.println(cart_itemDao.findCartVoByUser_id(1));
        //System.out.println(cart_itemDao.insertIntoCart_item(5, 3, 2));
        //System.out.println(cart_itemDao.deleteCart_item(20, 20));
        //System.out.println(cart_itemDao.getCart_itemCount());
        //System.out.println(cart_itemDao.insertIntoCart_item(6, 1, 1));
    }
}
