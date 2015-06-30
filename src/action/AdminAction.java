/**
 *@author Chu Zhang
 *This is the AdminAction.java file for our Auction-Webapp-Model project. 
 */
package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import sun.org.mozilla.javascript.internal.json.JsonParser.ParseException;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import dao.AdminDao;
import entity.Bid; 
import entity.Book;

public class AdminAction extends ActionSupport {
	private JSONObject resultObj;// The result json object to be passed into the jsp. 
	  
    public JSONObject getResultObj() {  
        return resultObj;  
    }  

    public void setResultObj(JSONObject resultObj) {  
        this.resultObj = resultObj;  
    }  

    /**
     *This method gets the bid history of a specific book
     */
	public String getBidHistoryById() { 
		System.out.println("entered getbidhistorybyid action");
        HttpServletRequest req = ServletActionContext.getRequest();
        String i = req.getParameter("id"); 
        System.out.println("id:" + i);
        int id = Integer.parseInt(i);
        HttpServletResponse resp = ServletActionContext.getResponse();  
        resp.setContentType("application/json");  
        ArrayList al = new ArrayList(); 
        
        AdminDao dao = new AdminDao();
        ArrayList<Bid> bidHis = dao.getBidHistoryById(id);  
        for (Bid b : bidHis) {  
            Map<String, Object> m = new HashMap<String, Object>();   
            m.put("id", b.getId());
            m.put("bidder", b.getBidder());  
            m.put("bid", b.getBid());
            m.put("time", (b.getTime()).toString());
            al.add(m); 
        }  
        Map<String, Object> json = new HashMap<String, Object>();  
        json.put("total", bidHis.size()); 
        json.put("rows", al); 
        resultObj = JSONObject.fromObject(json);
          
        return SUCCESS;  
    }

    /**
     *This method inserts the new book for auction into the database.
     */
    public String saveBook() throws java.text.ParseException {  
    	System.out.println("savebook action");
        HttpServletRequest req = ServletActionContext.getRequest();
        String name = req.getParameter("name");
        String descr = req.getParameter("descr");
        String startingPrice = req.getParameter("startingPrice");
        //System.out.println("before get time param");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        //System.out.println("startTime:"+startTime);
        String minIncre = req.getParameter("minIncre");
        double sp = Double.parseDouble(startingPrice);
        double mi = Double.parseDouble(minIncre);
        double highestBid = sp; //default highestBid is equal to the starting price

        //SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.fff");
        Timestamp st = new Timestamp(System.currentTimeMillis());//default start time
        Timestamp et = new Timestamp(System.currentTimeMillis());//default end time
        
        SimpleDateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
    	if (startTime != "")//if the startTime parameter is not empty, format it, convert it to a timestamp and assign it to st
	    {
	    	Date startT = originalFormat.parse(startTime);
	    	startTime = newFormat.format(startT);
	    	st = Timestamp.valueOf(startTime);
	    }
	    if (endTime != "")//if the endTime parameter is not empty, format it, convert it to a timestamp and assign it to et
	    {
	    	Date endT = originalFormat.parse(endTime);
	    	endTime = newFormat.format(endT);
	    	et = Timestamp.valueOf(endTime);
	    }
	    System.out.println("startTime:"+st.toString());
        AdminDao dao = new AdminDao();
        
        int c = dao.saveBook(name, descr, sp, st, et, mi, highestBid);
        System.out.println("c=" + c);
        Map<String, Object> json = new HashMap<String, Object>();  
        if (c > 0)
            json.put("success", true);
        else
            json.put("msg", "oops");
        resultObj = JSONObject.fromObject(json); 
        
        return SUCCESS;
    }

    /**
     *This method updates a book
     */
    public String updateBook() throws java.text.ParseException {
        HttpServletRequest req = ServletActionContext.getRequest();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String descr = req.getParameter("descr");
        String startingPrice = req.getParameter("startingPrice");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String minIncre = req.getParameter("minIncre");
        int idx = Integer.parseInt(id);
        double sp = Double.parseDouble(startingPrice);
        double mi = Double.parseDouble(minIncre);
        double highestBid = sp;

        //SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.fff");
        Timestamp st = new Timestamp(System.currentTimeMillis());//default start time
        Timestamp et = new Timestamp(System.currentTimeMillis());//default end time
        
        SimpleDateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
    	if (startTime != "")//if the startTime parameter is not empty, format it, convert it to a timestamp and assign it to st
	    {
	    	Date startT = originalFormat.parse(startTime);
	    	startTime = newFormat.format(startT);
	    	st = Timestamp.valueOf(startTime);
	    }
	    if (endTime != "")//if the endTime parameter is not empty, format it, convert it to a timestamp and assign it to et
	    {
	    	Date endT = originalFormat.parse(endTime);
	    	endTime = newFormat.format(endT);
	    	et = Timestamp.valueOf(endTime);
	    }
	    System.out.println("startTime:"+st.toString());
        AdminDao dao = new AdminDao();
        int c = dao.editBook(idx, name, descr, sp, st, et, mi);
        Map<String, Object> json = new HashMap<String, Object>();  
        if (c > 0)
            json.put("success", true);
        else
            json.put("msg", "oops");
        resultObj = JSONObject.fromObject(json);// 鏍煎紡鍖杛esult涓�畾瑕佹槸JSONObject  
        
        return SUCCESS;
    }

    /**
     *This method deletes the selected book
     */
    public String removeBook() {
    	System.out.println("enter removebook action");
        HttpServletRequest req = ServletActionContext.getRequest();
        String id = req.getParameter("id");
        
        AdminDao dao = new AdminDao();
        int idx = Integer.parseInt(id);
        int c = dao.removeBook(idx);
        Map<String, Object> json = new HashMap<String, Object>();  
        if (c > 0)
            json.put("success", true);
        else
            json.put("msg", "oops");
        resultObj = JSONObject.fromObject(json);// 鏍煎紡鍖杛esult涓�畾瑕佹槸JSONObject  
        
        return SUCCESS;
    }
}