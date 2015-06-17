package entity;
import java.util.Date;

public class Bid {
	private int id;
	private String bidder;
	private Double bid;
	private Date time;
	
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
	
	public void setTime(Date t) {
		time = t;
	}
	
	public Date getTime() {
		return time;
	}
}
