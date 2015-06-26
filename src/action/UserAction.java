package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import dao.UserDao;
import entity.Bid;
import entity.Book;

public class UserAction extends ActionSupport {
	private JSONObject resultObj;// 鐟曚浇绻戦崶鐐插煂妞ょ敻娼伴惃鍑ON閺佺増宓侀敍灞肩鐎规俺顩﹂張濉痚tter,setter閺傝纭堕妴锟� 
	  
    public JSONObject getResultObj() {  
        return resultObj;  
    }  

    public void setResultObj(JSONObject resultObj) {  
        this.resultObj = resultObj;  
    }  

	public String getBook() {
		System.out.println("getBook...");

        HttpServletResponse resp = ServletActionContext.getResponse();  
        resp.setContentType("application/json");  
        ArrayList al = new ArrayList(); 
        
        UserDao dao = new UserDao();
        ArrayList<Book> books = dao.getBook();  
        for (Book b : books) {  
            Map<String, Object> m = new HashMap<String, Object>();   
            m.put("id", b.getId());
            m.put("name", b.getName());  
            m.put("descr", b.getDescr());
            m.put("startingPrice", b.getStartingPrice());
            m.put("startTime", (b.getStartTime()).toString());
            m.put("endTime", (b.getEndTime()).toString());
            m.put("minIncre", b.getMinIncre());
            m.put("highestBid", b.getHighestBid());
            al.add(m); 
        }  
        Map<String, Object> json = new HashMap<String, Object>();  
        json.put("total", books.size());// total闁匡拷鐎涙ɑ鏂侀幀鏄忣唶瑜版洘鏆�  
        json.put("rows", al);// rows闁匡拷鐎涙ɑ鏂佸В蹇涖�夌拋鏉跨秿 list閿涘苯绻�妞ょ粯妲搁垾娓瀘ws閳ユ繂鍙ч柨顔跨槤  
        resultObj = JSONObject.fromObject(json);// 閺嶇厧绱￠崠鏉沞sult娑擄拷鐣剧憰浣规ЦJSONObject  
          
        return SUCCESS;  
    }

    public String placeBid() {  
    	HttpServletRequest req = ServletActionContext.getRequest();
    	String bidder = req.getParameter("bidder");
    	String bid = req.getParameter("bid");
    	String id = req.getParameter("id");
    	System.out.println("bidder:"+bidder);

    	//SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Timestamp d = new Timestamp(System.currentTimeMillis());
    	//String time = f.format(d);

    	UserDao dao = new UserDao();
    	int idx = Integer.parseInt(id);
    	double bidVal = Double.parseDouble(bid);
    	Book b = dao.getBookById(idx);
    	Timestamp st = b.getStartTime();
    	Timestamp et = b.getEndTime();
    	double mi = b.getMinIncre();
    	double hb = b.getHighestBid();

    	/*Date sd = null;
    	Date ed = null;

    	try {
            sd = f.parse(st);
            ed = f.parse(et);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

    	Map<String, Object> json = new HashMap<String, Object>();  
        
    	if (bidVal < hb+mi)
    		json.put("msg", "Your bid must be at least "+mi+"higher than the highest bid.");
    	else if (d.before(st)||d.after(et))
    		json.put("msg", "Watch the start and end time!");
    	else{
    		dao = new UserDao();
    		int c = dao.addBid(idx, bidder, bidVal, d);
	    	if (c > 0)
	    		json.put("success", true);
	    	else
	    		json.put("msg", "oops");
    	}
    	resultObj = JSONObject.fromObject(json);// 閺嶇厧绱￠崠鏉沞sult娑擄拷鐣剧憰浣规ЦJSONObject  
    	
        return SUCCESS;
    }
    
    public String closedAuctionAlert() {
    	System.out.println("entered closed auction alert action");
    	HttpServletResponse resp = ServletActionContext.getResponse();  
        resp.setContentType("application/json");  
        String message = "";
        boolean alert = false;
        
        UserDao dao = new UserDao();
        ArrayList<Book> books = dao.getBook();   
        
        Timestamp d = new Timestamp(System.currentTimeMillis());
        
        for ( int i = 0; i < books.size(); i++ )
        {
        	Book currBook = books.get(i);
        	dao = new UserDao();
        	Bid finalBid = dao.getFinalBid(currBook.getId(), currBook.getHighestBid());
        	Timestamp et = currBook.getEndTime();
        	long diff = d.getTime() - et.getTime();
        	if ( diff>=0 && diff<60000)
        	{
        		alert = true;
        		message += "The auction for "+currBook.getName()+" has ended.\n";
        		//System.out.println("finalbidder:" + finalBid.getBidder());
        		if (finalBid != null && finalBid.getBidder() != null)
        			message += "This book has been sold to "+finalBid.getBidder()+" for "+currBook.getHighestBid()+" yuan.\n";
        	}
        	else if ( diff < 0 && (finalBid == null || d.getTime() - finalBid.getTime().getTime() > 7200000 ))
        	{
        		alert = true;
        		dao = new UserDao();
        		dao.closeAuction(currBook.getId(), d);
        		message += "The auction for "+currBook.getName()+" has been idle for over two hours and is now ended.\n";
        	}
        }
        
        Map<String, Object> json = new HashMap<String, Object>();  
        json.put("success", alert);// total闁匡拷鐎涙ɑ鏂侀幀鏄忣唶瑜版洘鏆�  
        json.put("msg", message);// rows闁匡拷鐎涙ɑ鏂佸В蹇涖�夌拋鏉跨秿 list閿涘苯绻�妞ょ粯妲搁垾娓瀘ws閳ユ繂鍙ч柨顔跨槤  
        resultObj = JSONObject.fromObject(json);// 閺嶇厧绱￠崠鏉沞sult娑擄拷鐣剧憰浣规ЦJSONObject  
          
        return SUCCESS;
    }
}
