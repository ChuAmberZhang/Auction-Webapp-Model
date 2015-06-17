package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import dao.AdminDao;
import entity.Bid; 
import entity.Book;

public class AdminAction extends ActionSupport {
	private JSONObject resultObj;// 瑕佽繑鍥炲埌椤甸潰鐨凧SON鏁版嵁锛屼竴瀹氳鏈塯etter,setter鏂规硶銆� 
	  
    public JSONObject getResultObj() {  
        return resultObj;  
    }  

    public void setResultObj(JSONObject resultObj) {  
        this.resultObj = resultObj;  
    }  

	public String getBidHistoryById() { 
        HttpServletRequest req = ServletActionContext.getRequest();
        String i = req.getParameter("id"); 
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
        json.put("total", bidHis.size());// total閿�瀛樻斁鎬昏褰曟暟  
        json.put("rows", al);// rows閿�瀛樻斁姣忛〉璁板綍 list锛屽繀椤绘槸鈥渞ows鈥濆叧閿瘝  
        resultObj = JSONObject.fromObject(json);// 鏍煎紡鍖杛esult涓�畾瑕佹槸JSONObject  
          
        return SUCCESS;  
    }

    public String saveBook() {  
        HttpServletRequest req = ServletActionContext.getRequest();
        String name = req.getParameter("name");
        String desc = req.getParameter("desc");
        String startingPrice = req.getParameter("startingPrice");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String minIncre = req.getParameter("minIncre");
        double sp = Double.parseDouble(startingPrice);
        double mi = Double.parseDouble(minIncre);
        double highestBid = sp;

        //SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.fff");
        Timestamp st = new Timestamp(System.currentTimeMillis());
        Timestamp et = new Timestamp(System.currentTimeMillis());
        
        if (startTime != null)
        	st = Timestamp.valueOf(startTime);
        if (endTime != null)
        	et = Timestamp.valueOf(endTime);
        AdminDao dao = new AdminDao();
        
        int c = dao.saveBook(name, desc, sp, st, et, mi, highestBid);
        Map<String, Object> json = new HashMap<String, Object>();  
        if (c > 0)
            json.put("success", true);
        else
            json.put("msg", "oops");
        resultObj = JSONObject.fromObject(json);// 鏍煎紡鍖杛esult涓�畾瑕佹槸JSONObject  
        
        return SUCCESS;
    }

    public String updateBook() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String desc = req.getParameter("desc");
        String startingPrice = req.getParameter("startingPrice");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String minIncre = req.getParameter("minIncre");
        int idx = Integer.parseInt(id);
        double sp = Double.parseDouble(startingPrice);
        double mi = Double.parseDouble(minIncre);
        double highestBid = sp;

        //SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.fff");
        Timestamp st = new Timestamp(System.currentTimeMillis());
        Timestamp et = new Timestamp(System.currentTimeMillis());
        
        if (startTime != null)
        	st = Timestamp.valueOf(startTime);
        if (endTime != null)
        	et = Timestamp.valueOf(endTime);
        
        AdminDao dao = new AdminDao();
        int c = dao.editBook(idx, name, desc, sp, st, et, mi);
        Map<String, Object> json = new HashMap<String, Object>();  
        if (c > 0)
            json.put("success", true);
        else
            json.put("msg", "oops");
        resultObj = JSONObject.fromObject(json);// 鏍煎紡鍖杛esult涓�畾瑕佹槸JSONObject  
        
        return SUCCESS;
    }

    public String removeBook() {
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