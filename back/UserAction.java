package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import dao.UserDao;
import entity.User;

public class UserAction extends ActionSupport {
	private JSONObject resultObj;// 要返回到页面的JSON数据，一定要有getter,setter方法。  
	  
    public JSONObject getResultObj() {  
        return resultObj;  
    }  

    public void setResultObj(JSONObject resultObj) {  
        this.resultObj = resultObj;  
    }  

	public String getBook() {  
        HttpServletResponse resp = ServletActionContext.getResponse();  
        resp.setContentType("application/json");  
        ArrayList al = new ArrayList(); 
        
        UserDao dao = new UserDao();
        ArrayList<Book> books = dao.getBook();  
        for (Book b : books) {  
            Map<String, Object> m = new HashMap<String, Object>();   
            m.put("id", b.getId());
            m.put("name", b.getName());  
            m.put("desc", b.getDesc());
            m.put("startingPrice", b.getStartingPrice());
            m.put("startTime", b.getStartTime());
            m.put("endTime", b.getEndTime());
            m.put("minIncre", b.getMinIncre());
            m.put("highestBid", b.getHighestBid());
            al.add(m); 
        }  
        Map<String, Object> json = new HashMap<String, Object>();  
        json.put("total", books.size());// total键 存放总记录数  
        json.put("rows", al);// rows键 存放每页记录 list，必须是“rows”关键词  
        resultObj = JSONObject.fromObject(json);// 格式化result一定要是JSONObject  
          
        return SUCCESS;  
    }

    public String placeBid() {  
    	HttpServletRequest req = ServletActionContext.getRequest();
    	String name = req.getParameter("name");
    	String bid = req.getParameter("bid");
    	String id = req.getParameter("id");

    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date d = new Date();
    	String time = f.format(d);

    	UserDao dao = new UserDao();
    	int idx = Integer.parseInt(id);
    	int bidVal = Integer.parseInt(bid);
    	Book b = dao.getBookById(idx);
    	String st = b.getStartTime();
    	String et = b.getEndTime();
    	int mi = b.getMinIncre();
    	int hb = b.getHighestBid();

    	Date sd = null;
    	Date ed = null;

    	try {
            sd = f.parse(st);
            ed = f.parse(et);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    	if (bidVal < hb+mi)
    		json.put("msg", "Your bid must be at least "+mi+"higher than the highest bid.");
    	else if (d.before(sd)||d.after(ed))
    		json.put("msg", "Watch the start and end time!");
    	else{
    		int c = dao.addBid(idx, name, bidVal, time);
	    	Map<String, Object> json = new HashMap<String, Object>();  
	    	if (c > 0)
	    		json.put("success", true);
	    	else
	    		json.put("msg", "oops");
    	}
    	resultObj = JSONObject.fromObject(json);// 格式化result一定要是JSONObject  
    	
        return SUCCESS;
    }
}
