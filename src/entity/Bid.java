package entity;
import java.sql.Timestamp;

public class Bid {
	private int id;
	private String bidder;
	private Double bid;
	private Timestamp time;
	
	public void setId(int i) {
		id = i;
	}
	
	public int getId() {
		return id;
	}
	
	public void setBidder(String b) {
		bidder = b;
	}
	
	public String getBidder() {
		return bidder;
	}
	
	public void setBid(Double n) {
		bid = n;
	}
	
	public Double getBid() {
		return bid;
	}
	
	public void setTime(Timestamp t) {
		time = t;
	}
	
	public Timestamp getTime() {
		return time;
	}
}
