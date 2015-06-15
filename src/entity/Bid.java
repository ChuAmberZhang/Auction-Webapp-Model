package entity;

public class Bid {
	private int id;
	private String bidder;
	private int bid;
	private String time;
	
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
	
	public void setBid(int n) {
		bid = n;
	}
	
	public int getBid() {
		return bid;
	}
	
	public void setTime(String t) {
		time = t;
	}
	
	public String getTime() {
		return time;
	}
}
