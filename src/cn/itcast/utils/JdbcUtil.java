package cn.itcast.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 1. 返回连接 2. 关闭
 *
 */
public class JdbcUtil {

    //连接参数
    //private String url = "jdbc:mysql://localhost:3306/jdbc_demo";
    private static String url = "jdbc:mysql:///jdbc_demo";
    private static String user = "root";
    private static String password = "root";

    /**
     * 返回连接对象
     */
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url,user,password);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭
     */
    public static void closeAll(Connection con, Statement stmt, ResultSet rs){
        try {
            if (rs != null){
                rs.close();// 快速异常捕获 Alt + shift + z
                rs = null;// 建议垃圾回收期回收资源
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (con != null && !con.isClosed()) {
                con.close();
                con = null;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
