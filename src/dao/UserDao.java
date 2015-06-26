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
				b.setDescr(rs.getString(3));
				b.setStartingPrice(rs.getDouble(4));
				b.setStartTime(rs.getTimestamp(5));
				b.setEndTime(rs.getTimestamp(6));
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
				b.setDescr(rs.getString(3));
				b.setStartingPrice(rs.getDouble(4));
				b.setStartTime(rs.getTimestamp(5));
				b.setEndTime(rs.getTimestamp(6));
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

	public int addBid(int id, String bidder, double bid, Timestamp time) {
		System.out.println("entered addbid dao");
		String sql = "insert into bid_history (id, bidder, bid, time) values (?, ?, ?, ?)";
		String sql2 = "update books set highestBid = ? where id = ?";
		int result = 0; 
		int result2 = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, bidder);
			ps.setDouble(3, bid);
			ps.setTimestamp(4, time);
			System.out.println(ps.toString());
			result = ps.executeUpdate();
			
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setDouble(1, bid);
			ps2.setInt(2, id);
			System.out.println(ps2.toString());
			result2 = ps2.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}
	
	public Bid getFinalBid(int id, double highestBid) {
		System.out.println("Entered getfinalbid dao");
		Bid finalBid = new Bid();
		String sql = "select * from bid_history where (id, bid) = (?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setDouble(2, highestBid);
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				finalBid.setId(rs.getInt(1));
				finalBid.setBidder(rs.getString(2));
				finalBid.setBid(rs.getDouble(3));
				finalBid.setTime(rs.getTimestamp(4));
			}
			else finalBid = null;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return finalBid;
	}
	
	public void closeAuction(int id, Timestamp currTime) {
		System.out.println("entered close auction");
		String sql = "update books set endTime = ? where id = ?";
		int result = 0;
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, currTime);
			ps.setInt(2, id);
			System.out.println(ps.toString());
			result = ps.executeUpdate();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return;
	}
	
	/*public ArrayList<Map> closedAuctionAlert() {
		System.out.println("entered closedAuctionAlert dao");
		ArrayList endTimes = new ArrayList();
		String sql = "select endTime from books";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Map<String, Object> m = new HashMap<String, Object>();
				endTimes.add(rs.getTimestamp(1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return endTimes;
		
	}*/
}