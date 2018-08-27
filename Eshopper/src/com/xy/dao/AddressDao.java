package com.xy.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xy.utils.DBUtils;

import com.xy.po.Adress;
import com.xy.po.User;

public class AddressDao {

	User user=new User();
	public int getAddressConn() {
		Connection connection=DBUtils.getConnection();
		String sql="select count(*) from address";
		PreparedStatement ps = null;
        ResultSet rs = null;
        int rowCount = 0;
        try{
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            rowCount = rs.getInt(1);
            //conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(rs, ps, connection);
        }
        return rowCount;
		
	}
	
	public boolean updateAddres(Adress address,int user_id) {
		Connection conn=DBUtils.getConnection();
		String sql="update address a set a.`province`=?,a.`city`=?,a.`detail`=?,a.`zip`=? where user_id=?" ;
		PreparedStatement ps=null;
	
		 try{
	            ps = conn.prepareStatement(sql);
	          
	           /* ps.setString(1, user.getUsername());
	            ps.setString(2, user.getEmail());
	            ps.setString(3, user.getTelephone());
	            ps.setString(4, user.getSex());
	            ps.setString(5, user.getBirthday());
	          */
	            ps.setString(1, address.getProvince());
	            ps.setString(2, address.getCity());
	            ps.setString(3, address.getDetail());
	            ps.setString(4, address.getZip());
	            ps.setInt(5, user_id);
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
	 * ����id��ѯ
	 * @param id
	 * @return
	 */
	public Adress findByUid(int user_id) {
		Connection conn = DBUtils.getConnection();
		String sql = "select a.`province`,a.`city`,a.`detail`,a.`zip` FROM address a,user u where a.user_id=u.user_id AND u.user_id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			if(rs.next()) {
				return new Adress(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection(rs, ps, conn);
		}
		return null;
	}

  
	public static void main(String[] args) {
		AddressDao addressDao=new AddressDao();
		//addressDao.findByUid(1);
		addressDao.updateAddres(new Adress("ww","www","www","ww"),2);
	}

}
