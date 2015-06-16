package entity;

public class Book {
	private int id;
	private String name;
	private String desc;
	private int startingPrice;
	private String startTime;
	private String endTime;
	private int minIncre;
	private int highestBid;
	
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
	
	public void setStartingPrice(int s) {
		startingPrice = s;
	}
	
	public int getStartingPrice() {
		return startingPrice;
	}
	
	public void setStartTime(String t) {
		startTime = t;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setEndTime(String t) {
		endTime = t;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setMinIncre(int m) {
		minIncre = m;
	}
	
	public int getMinIncre() {
		return minIncre;
	}
	
	public void setHighestBid(int h) {
		highestBid = h;
	}
	
	public int getHighestBid() {
		return highestBid;
	}
}
