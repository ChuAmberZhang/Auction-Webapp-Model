package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Bid;
import entity.Book;
import util.DB;

public class AdminDao {
	private DB db;
	private Connection conn = null;
	
	public AdminDao() {
		db = new DB();
		conn = db.getConn();
	}

	public ArrayList<Bid> getBidHistoryById(int id) {
		ArrayList<Bid> bidHis = new ArrayList<Bid>();
		String sql = "select * from bid_history where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Bid b = new Bid();
				b.setId(rs.getInt(1));
				b.setBidder(rs.getString(2));
				b.setBid(rs.getDouble(3));
				b.setTime(rs.getTimestamp(4));
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

	public int saveBook(String name, String descr, double sp, Timestamp startTime, Timestamp endTime, double mi, double highestBid) {
		String sql = "insert into books (name, descr, startingPrice, startTime, endTime, minIncre, highestBid) values (?, ?, ?, ?, ?, ?, ?)";
		int result = 0;
		System.out.println("entered savebook dao");
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, descr);
			ps.setDouble(3, sp);
			ps.setTimestamp(4, startTime);
			ps.setTimestamp(5, endTime);
			ps.setDouble(6, mi);
			ps.setDouble(7, highestBid);
			System.out.println(ps.toString());
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public int editBook(int idx, String name, String descr, double sp, Timestamp startTime, Timestamp endTime, double mi) {
		String sql = "update books set name = ?, descr = ?, startingPrice = ?, startTime = ?, endTime = ?, minIncre = ? where id = ?";
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, descr);
			ps.setDouble(3, sp);
			ps.setTimestamp(4, startTime);
			ps.setTimestamp(5, endTime);
			ps.setDouble(6, mi);
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
		String sql = "delete books.* from books where id = ?";
		//System.out.println(id);
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			//System.out.print(ps);
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("exception message");
		} finally {
			db.close();
		}
		return result;
	}
}