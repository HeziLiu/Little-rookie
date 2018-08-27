package com.xy.utils;

import java.sql.*;

/**
 * 数据库工具类
 */
public class DBUtils {
    static private String driver = "com.mysql.jdbc.Driver";
    static private String url = "jdbc:mysql://localhost:3306/e_shopper?useSSL=false";
    static private String username = "root";
    static private String password = "password";

    static {
        try{
            // 加载驱动
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    // 建立连接
    public static Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, username, password);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭连接
    public static void closeConnection(ResultSet rs, PreparedStatement ps, Connection conn){
        if(rs != null){
            try{
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        if(ps != null){
            try{
                ps.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Connection connection = null;
        System.out.println(DBUtils.getConnection());
    }

}
