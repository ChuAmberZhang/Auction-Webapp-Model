package util;

import javax.naming.*;
import javax.sql.DataSource;
import java.sql.Connection;

public class DB {
	private  Connection conn = null;
	
	public   Connection getConn() {
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EShop");
			conn = ds.getConnection();
			return conn;
		} catch (Exception e) {
			System.err.println("数据库连接异常: " + e.getMessage());
			return null;
		}
	}
	
	public void close() {
		try {
			conn.close();
		} catch (Exception e) {
			System.err.println("数据库连接关闭异常: " + e.getMessage());
		}
	}
}
