package prediction.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import prediction.dao.CustInfoDao;
import prediction.model.CustInfo;

/**
 * Servlet implementation class CustInfoServlet
 */
@WebServlet("/")
public class CustInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CustInfoDao custInfoDao;
	
    public CustInfoServlet() {
        this.custInfoDao = new CustInfoDao();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		
		switch (action) {
		case "/insert":
			try {
				insertCust(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/edit" :
			try {
				updateCust(request, response);
			} catch (SQLException | IOException | JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			
		case "/advSearch" :
			try {
				advSearch(request, response);
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		case "/search":
			try {
				custIdSearch(request, response);
			} catch (JSONException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		case "/delete" :
			try {
				deleteCust(request, response);
			} catch (IOException | JSONException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		case "/analytics" :
			
			try {
				analytics(request, response);
			} catch (IOException | JSONException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			break;
		
		case "/predict" :
			try {
				getPredictData(request, response);
			} catch (JSONException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/agingBucket" :
			
			try {
				agingBucket(request, response);
			} catch (SQLException | IOException | ServletException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
			try {
				getCust(request, response);
			} catch (SQLException | IOException | ServletException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}
	
	//Insert Data
	private void insertCust(HttpServletRequest request, HttpServletResponse response)
		throws SQLException, IOException, ServletException, JSONException {
		
	    StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
	    String data = buffer.toString();
	    JSONObject jsonObject = new JSONObject(data);

				
	    String business_code = jsonObject.getString("business_code");
	    String cust_number = jsonObject.getString("cust_number");
	    String clear_date = jsonObject.getString("clear_date");
	    String buisness_year = jsonObject.getString("buisness_year");
	    String doc_id = jsonObject.getString("doc_id");
	    String posting_date = jsonObject.getString("posting_date");
	    String document_create_date = jsonObject.getString("document_create_date");
	    String due_in_date = jsonObject.getString("due_in_date");
	    String invoice_currency = jsonObject.getString("invoice_currency");
	    String document_type = jsonObject.getString("document_type");
	    String posting_id = jsonObject.getString("posting_id");
	    String total_open_amount= jsonObject.getString("total_open_amount");
	    String baseline_create_date = jsonObject.getString("baseline_ceate_date");
	    String cust_payment_terms = jsonObject.getString("cust_payment_terms");
	    String invoice_id = jsonObject.getString("invoice_id");
	    
	    CustInfo custInfo = new CustInfo();
	    
	    custInfo.setBusiness_code(business_code);
	    custInfo.setCust_number(cust_number);
	    custInfo.setClear_date(clear_date);
	    custInfo.setBuisness_year(buisness_year);
	    custInfo.setDoc_id(doc_id);
	    custInfo.setPosting_date(posting_date);
	    custInfo.setDocument_create_date(document_create_date);
	    custInfo.setDue_in_date(due_in_date);
	    custInfo.setInvoice_currency(invoice_currency);
	    custInfo.setDocument_type(document_type);
	    custInfo.setPosting_id(posting_id);
	    custInfo.setTotal_open_amount(total_open_amount);
	    custInfo.setBaseline_create_date(baseline_create_date);
	    custInfo.setCust_payment_terms(cust_payment_terms);
	    custInfo.setInvoice_id(invoice_id);
	    
	    custInfoDao.insertCust(custInfo);
	}
	
	//Update Data
	private void updateCust(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, JSONException {
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
	    String data = buffer.toString();
	    JSONObject jsonObject = new JSONObject(data);
	    
		String sl_no = jsonObject.getString("sl_no");
	    String invoice_currency = jsonObject.getString("invoice_currency");
	    String cust_payment_terms = jsonObject.getString("cust_payment_terms");
	    
	    CustInfo custInfo = new CustInfo();
	    
	    custInfo.setSl_no(sl_no);
	    custInfo.setInvoice_currency(invoice_currency);
	    custInfo.setCust_payment_terms(cust_payment_terms);
	    
	    custInfoDao.updateCust(custInfo);
	}
	
	//Pagination Data
	private void getCust(HttpServletRequest request, HttpServletResponse response)
		throws SQLException, IOException, ServletException, JSONException {
		
		String spageid = request.getParameter("page");
	    String stotal = request.getParameter("pageSize");
	    String sort = request.getParameter("sort");
	    String order = request.getParameter("order");
	    
	    int pageid = Integer.parseInt(spageid);
	    int total = Integer.parseInt(stotal);
	    
	    pageid = pageid*total;
	    
	    Object[] custInfos = CustInfoDao.getCust(pageid, total,sort,order);
	    
	    Gson gson = new Gson();
		String userJSON = gson.toJson(custInfos);
		
		PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		printWriter.write(userJSON);
		printWriter.close();
	}
	
	//Search By Cust_ID
	private void custIdSearch(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
		
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
	    String data = buffer.toString();
	    JSONObject jsonObject = new JSONObject(data);
	    
	    String spageid = request.getParameter("page");
	    String stotal = request.getParameter("pageSize");
	    String sort = request.getParameter("sort");
	    String order = request.getParameter("order");
	    
	    
	    int pageid = Integer.parseInt(spageid);
	    int total = Integer.parseInt(stotal);
	    	    
	    pageid = pageid*total;
	    
	    String cust_number = jsonObject.getString("cust_number");
	    
	    CustInfo custInfo = new CustInfo();
	    
	    custInfo.setCust_number(cust_number);
	    
	    Object[] searchList =CustInfoDao.custIdSearch(pageid,total,custInfo,sort,order); 
	    
	    Gson gson = new Gson();
		String userJSON = gson.toJson(searchList);
		
		PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		printWriter.write(userJSON);
		printWriter.close();
	}
	
	//Advance Search Data
	private void advSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
		
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
	    String data = buffer.toString();
	    JSONObject jsonObject = new JSONObject(data);
	    
	    String spageid = request.getParameter("page");
	    String stotal = request.getParameter("pageSize");
	    String sort = request.getParameter("sort");
	    String order = request.getParameter("order");
	    
	    int pageid = Integer.parseInt(spageid);
	    int total = Integer.parseInt(stotal);
	    	    

	    pageid = pageid*total;

	    
	    String cust_number = jsonObject.getString("cust_number");
	    String buisness_year = jsonObject.getString("buisness_year");
	    String doc_id = jsonObject.getString("doc_id");
	    String invoice_id = jsonObject.getString("invoice_id");
	    
	    CustInfo custInfo = new CustInfo();
	    
	    custInfo.setCust_number(cust_number);
	    custInfo.setBuisness_year(buisness_year);
	    custInfo.setDoc_id(doc_id);
	    custInfo.setInvoice_id(invoice_id);
	    
	    Object[] advList =CustInfoDao.advSearch(pageid,total,custInfo,order,sort); 
	    
	    Gson gson = new Gson();
		String userJSON = gson.toJson(advList);
		
		PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		printWriter.write(userJSON);
		printWriter.close();
	}
	
	//Delete User
	public void deleteCust(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException, SQLException {
		
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
	    String data = buffer.toString();
	    JSONObject jsonObject = new JSONObject(data);
	    
	    JSONArray sl_no = jsonObject.getJSONArray("sl_no");
	    
	    CustInfoDao.deleteUser(sl_no);
	}
	
	//Analytics
	public void analytics(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException, SQLException {
		
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
	    String data = buffer.toString();
	    JSONObject jsonObject = new JSONObject(data);
	    
	    String invoice_currency = jsonObject.getString("invoice_currency");
	    String clear_date1 = jsonObject.getString("clear_date1");
	    String clear_date2 = jsonObject.getString("clear_date2");
	    String baseline_ceate_date1 = jsonObject.getString("baseline_ceate_date1");
	    String baseline_ceate_date2 = jsonObject.getString("baseline_ceate_date2");
	    String due_in_date1 = jsonObject.getString("due_in_date1");
	    String due_in_date2 = jsonObject.getString("due_in_date2");
	    
	    Object obj = CustInfoDao.BarAnalytics(invoice_currency,clear_date1,clear_date2,baseline_ceate_date1,baseline_ceate_date2,due_in_date1,due_in_date2);
	    System.out.println(obj);
	    
	    Gson gson = new Gson();
		String userJSON = gson.toJson(obj);
		
		PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		printWriter.write(userJSON);
		printWriter.close();
	    
	}
	
	private void getPredictData(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException, SQLException {
		
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
	    String data = buffer.toString();
	    JSONObject jsonObject = new JSONObject(data);
	    
	    JSONArray sl_no = jsonObject.getJSONArray("sl_no");
	    
	    List <CustInfo> predictList = CustInfoDao.getPredictData(sl_no);
	    
	    Gson gson = new Gson();
		String userJSON = gson.toJson(predictList);
		
		PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		printWriter.write(userJSON);
		printWriter.close();
	}
	
	//Insert Aging Bucket
		private void agingBucket(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, JSONException {
			
		    StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		        buffer.append(System.lineSeparator());
		    }
		    String data = buffer.toString();
		    JSONObject jsonObject = new JSONObject(data);

					
		    String doc_id = jsonObject.getString("doc_id");
		    String aging_bucket = jsonObject.getString("aging_bucket");
		    
		    CustInfo custInfo = new CustInfo();
		    
		    custInfo.setDoc_id(doc_id);
		    custInfo.setAging_bucket(aging_bucket);
		    
		    custInfoDao.setAgingBucket(custInfo);
		}
}
