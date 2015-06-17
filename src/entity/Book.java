package entity;
import java.sql.Timestamp;

public class Book {
	private int id;
	private String name;
	private String desc;
	private double startingPrice;
	private Timestamp startTime;
	private Timestamp endTime;
	private double minIncre;
	private double highestBid;
	
	public void setId(int i) {
		id = i;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}

	public void setDesc(String d) {
		desc = d;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setStartingPrice(double s) {
		startingPrice = s;
	}
	
	public double getStartingPrice() {
		return startingPrice;
	}
	
	public void setStartTime(Timestamp t) {
		startTime = t;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	
	public void setEndTime(Timestamp t) {
		endTime = t;
	}
	
	public Timestamp getEndTime() {
		return endTime;
	}
	
	public void setMinIncre(double m) {
		minIncre = m;
	}
	
	public double getMinIncre() {
		return minIncre;
	}
	
	public void setHighestBid(double h) {
		highestBid = h;
	}
	
	public double getHighestBid() {
		return highestBid;
	}
}
