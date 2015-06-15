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

import dao.AdminDao;
import entity.Bidder;

public class AdminAction extends ActionSupport {
	private JSONObject resultObj;// 要返回到页面的JSON数据，一定要有getter,setter方法。  
	  
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
        
        UserDao dao = new AdminDao();
        ArrayList<Bidder> bidHis = dao.getBidHisotryById(id);  
        for (Bidder b : bidHis) {  
            Map<String, Object> m = new HashMap<String, Object>();   
            m.put("id", b.getId());
            m.put("bidder", b.getBidder());  
            m.put("bid", b.getBid());
            m.put("time", b.getTime());
            al.add(m); 
        }  
        Map<String, Object> json = new HashMap<String, Object>();  
        json.put("total", bidHis.size());// total键 存放总记录数  
        json.put("rows", al);// rows键 存放每页记录 list，必须是“rows”关键词  
        resultObj = JSONObject.fromObject(json);// 格式化result一定要是JSONObject  
          
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
        int sp = Integer.parseInt(startingPrice);
        int mi = Integer.parseInt(minIncre);
        int highestBid = sp;

        UserDao dao = new AdminDao();
        
        int c = dao.saveBook(name, desc, sp, startTime, endTime, mi, highestBid);
        Map<String, Object> json = new HashMap<String, Object>();  
        if (c > 0)
            json.put("success", true);
        else
            json.put("msg", "oops");
        resultObj = JSONObject.fromObject(json);// 格式化result一定要是JSONObject  
        
        return SUCCESS;
    }

    public String editBook() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String desc = req.getParameter("desc");
        String startingPrice = req.getParameter("startingPrice");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String minIncre = req.getParameter("minIncre");
        int idx = Integer.parseInt(id);
        int sp = Integer.parseInt(startingPrice);
        int mi = Integer.parseInt(minIncre);
        
        
        UserDao dao = new AdminDao();
        int c = dao.editBook(idx, name, desc, sp, startTime, endTime, mi);
        Map<String, Object> json = new HashMap<String, Object>();  
        if (c > 0)
            json.put("success", true);
        else
            json.put("msg", "oops");
        resultObj = JSONObject.fromObject(json);// 格式化result一定要是JSONObject  
        
        return SUCCESS;
    }

    public String removeBook() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String id = req.getParameter("id");
        
        UserDao dao = new AdminDao();
        int idx = Integer.parseInt(id);
        int c = dao.removeBook(idx);
        Map<String, Object> json = new HashMap<String, Object>();  
        if (c > 0)
            json.put("success", true);
        else
            json.put("msg", "oops");
        resultObj = JSONObject.fromObject(json);// 格式化result一定要是JSONObject  
        
        return SUCCESS;
    }
}