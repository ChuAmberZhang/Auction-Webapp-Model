package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Bidder;
import util.DB;

public class AdminDao {
	private DB db;
	private Connection conn = null;
	
	public UserDao() {
		db = new DB();
		conn = db.getConn();
	}

	public ArrayList<Book> getBidHistoryById(int id) {
		ArrayList<Bidder> bidHis = new ArrayList<Bidder>();
		String sql = "select * from bid_history where id = ?";
		System.out.println(sql);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Bidder b = new Bidder();
				b.setId(rs.getInt(1));
				b.setBidder(rs.getString(2));
				b.setBid(rs.getInt(3));
				b.setTime(rs.getString(4));
				bidHis.add(b);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return bidHis;
	}

	public int saveBook(String name, String desc, int sp, String startTime, String endTime, int mi, int highestBid) {
		String sql = "insert into bid_history (name, desc, startingPrice, startTime, endTime, minIncre, highestBid) values (?, ?, ?, ?, ?, ?, ?)";
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, desc);
			ps.setInt(3, sp);
			ps.setString(4, startTime);
			ps.setString(5, endTime);
			ps.setInt(6, mi);
			ps.setInt(7, highestBid);

			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public int editBook(int idx, String name, String desc, int sp, String startTime, String endTime, int mi) {
		String sql = "update books set name = ?, desc = ? startingPrice = ? startTime = ? endTime = ? minIncre = ? where id = ?";
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, desc);
			ps.setInt(3, sp);
			ps.setString(4, startTime);
			ps.setString(5, endTime);
			ps.setInt(6, mi);
			ps.setInt(7, idx);
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public int removeBook(int id) {
		String sql = "delete from books where id = ?; delete from bid_history where id = ?";
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id);
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}
}