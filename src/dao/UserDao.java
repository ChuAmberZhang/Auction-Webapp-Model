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
import java.util.Date;

import entity.Book;
import entity.Bid;
import util.DB;

public class UserDao {
	private DB db;
	private Connection conn = null;
	
	public UserDao() {
		db = new DB();
		conn = db.getConn();
	}

	public ArrayList<Book> getBook() {
		ArrayList<Book> books = new ArrayList<Book>();
		String sql = "select * from books";
		System.out.println(sql);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt(1));
				b.setName(rs.getString(2));
				b.setDesc(rs.getString(3));
				b.setStartingPrice(rs.getDouble(4));
				b.setStartTime(rs.getDate(5));
				b.setEndTime(rs.getDate(6));
				b.setMinIncre(rs.getDouble(7));
				b.setHighestBid(rs.getDouble(8));
				books.add(b);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return books;
	}

	public Book getBookById(int id) {
		Book b = new Book();
		String sql = "select * from books where id = ?";
		System.out.println(sql);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				b.setId(rs.getInt(1));
				b.setName(rs.getString(2));
				b.setDesc(rs.getString(3));
				b.setStartingPrice(rs.getDouble(4));
				b.setStartTime(rs.getDate(5));
				b.setEndTime(rs.getDate(6));
				b.setMinIncre(rs.getDouble(7));
				b.setHighestBid(rs.getDouble(8));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return b;
	}

	public int addBid(int id, String name, double bid, Date time) {
		String sql = "insert into bid_history (id, name, bid, time) values (?, ?, ?, ?); update books set highestBid = ? where id = ?";
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setDouble(3, bid);
			ps.setDate(4, time);
			ps.setDouble(5, bid);
			ps.setInt(6, id);

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