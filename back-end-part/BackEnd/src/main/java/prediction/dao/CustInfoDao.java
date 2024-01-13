package prediction.dao;
import java.sql.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;



import prediction.model.CustInfo;

public class CustInfoDao {

	private static final String INSERT_USERS_SQL = "INSERT INTO winter_internship" +
	            " ( sl_no, business_code, cust_number, clear_date, buisness_year, doc_id, posting_date, document_create_date, due_in_date, invoice_currency, document_type, posting_id, total_open_amount, baseline_create_date, cust_payment_terms, invoice_id) VALUES " +
	            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_ALL_CUST = "select * from winter_internship WHERE (is_deleted = 0) ";
	private static final String SELECT_ADV_CUST = "select * from winter_internship WHERE is_deleted = 0";
	private static final String SELECT_SEARCH_CUST = "select * from winter_internship WHERE is_deleted = 0 AND cust_number = ? ";
	private static final String UPDATE = "update winter_internship set ";
	private static final String DELETE_INFO = "update winter_internship set is_deleted = 1 WHERE sl_no = ?";
	private static final String ANALYTICS = "WHERE (is_deleted = 0 AND (clear_date BETWEEN ? AND ?) AND (baseline_create_date BETWEEN ? AND ?) AND (due_in_date BETWEEN ? AND ?))";
	private static final String CURRENCY = "SELECT COUNT(invoice_currency) FROM winter_internship WHERE (is_deleted = 0 AND (clear_date BETWEEN ? AND ?) AND (baseline_create_date BETWEEN ? AND ?) AND (due_in_date BETWEEN ? AND ?)) GROUP BY invoice_currency";
	private static final String PREDICT_DATA = "select * from winter_internship WHERE sl_no = ? ";
	private static final String AGING_BUCKET = "update winter_internship set aging_bucket = ? WHERE doc_id = ?";
	private static final String ROWS = "SELECT COUNT(*) from winter_internship";
	
		
		
	protected static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");	
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grey_goose", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	//Paginated Data
		public static Object[] getCust(int start, int total, String sort, String order){
			
			List<CustInfo> custInfos = new ArrayList<CustInfo>();
			try {Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUST+" ORDER BY "+sort+" "+order+" limit "+(start)+","+total);
					System.out.println(preparedStatement);
					ResultSet rs = preparedStatement.executeQuery();
					while(rs.next()) {
						String sl_no = rs.getString("sl_no");
						String business_code = rs.getString("business_code");
						String cust_number = rs.getString("cust_number");
					    String clear_date = rs.getString("clear_date");
					    String buisness_year = rs.getString("buisness_year");
					    String doc_id = rs.getString("doc_id");
					    String posting_date = rs.getString("posting_date");
					    String document_create_date = rs.getString("document_create_date");
					    String document_create_date1 = rs.getString("document_create_date1");
					    String due_in_date = rs.getString("due_in_date");
					    String invoice_currency = rs.getString("invoice_currency");
					    String document_type = rs.getString("document_type"); 
					    String posting_id = rs.getString("posting_id");
					    String area_business = rs.getString("area_business");
					    String total_open_amount = rs.getString("total_open_amount");
					    String baseline_create_date = rs.getString("baseline_create_date");
					    String cust_payment_terms = rs.getString("cust_payment_terms");
					    String invoice_id = rs.getString("invoice_id");
					    String isOpen = rs.getString("isOpen");
					    String aging_bucket = rs.getString("aging_bucket");
					    String is_deleted = rs.getString("is_deleted");
					    custInfos.add(new CustInfo(sl_no, business_code, cust_number, clear_date, buisness_year, doc_id, posting_date, document_create_date, document_create_date1, due_in_date, invoice_currency, document_type, posting_id, area_business, total_open_amount, baseline_create_date, cust_payment_terms, invoice_id, isOpen, aging_bucket, is_deleted));
					}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			String rows = null;
			
			final String DISPLAY_ROWS = "SELECT COUNT(*) from winter_internship WHERE is_deleted = 0";
			try(Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(DISPLAY_ROWS)){
				
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					rows = rs.getString("COUNT(*)");
					System.out.println(rows);
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			return new Object[]{rows,custInfos};
		}

	
	//Insert New Customer Information
	public void insertCust(CustInfo custInfo) throws SQLException
	{
		
		int sl_no = 0;
		
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(ROWS)){
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
			sl_no = rs.getInt("COUNT(*)");
			sl_no = sl_no +1;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		try(Connection connection = getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
				
				preparedStatement.setInt(1, sl_no);
				preparedStatement.setString(2, custInfo.getBusiness_code());
				preparedStatement.setString(3, custInfo.getCust_number());
				preparedStatement.setString(4, custInfo.getClear_date());
				preparedStatement.setString(5, custInfo.getBuisness_year());
				preparedStatement.setString(6, custInfo.getDoc_id());
				preparedStatement.setString(7, custInfo.getPosting_date());
				preparedStatement.setString(8, custInfo.getDocument_create_date());
				preparedStatement.setString(9, custInfo.getDue_in_date());
				preparedStatement.setString(10, custInfo.getInvoice_currency());
				preparedStatement.setString(11, custInfo.getDocument_type());
				preparedStatement.setString(12, custInfo.getPosting_id());
				preparedStatement.setString(13, custInfo.getTotal_open_amount());
				preparedStatement.setString(14, custInfo.getBaseline_create_date());
				preparedStatement.setString(15, custInfo.getCust_payment_terms());
				preparedStatement.setString(16, custInfo.getInvoice_id());
				
				System.out.println(preparedStatement);
				
				preparedStatement.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Update Information of all Customer
	public void updateCust(CustInfo custInfo) throws SQLException {
		
		String UPDATE_INFO = UPDATE;
		
		if(custInfo.getInvoice_currency() != "" && custInfo.getCust_payment_terms() == "") {
			
			UPDATE_INFO+="invoice_currency = \""+custInfo.getInvoice_currency()+"\"";
		}
		
		else if(custInfo.getCust_payment_terms() != ""&& custInfo.getInvoice_currency() == "") {
			
			UPDATE_INFO+=" cust_payment_terms = \""+custInfo.getCust_payment_terms()+"\"";
		}
		else {
			
			UPDATE_INFO+="invoice_currency = \""+custInfo.getInvoice_currency()+"\""+", cust_payment_terms = \""+custInfo.getCust_payment_terms()+"\"";
		}
		
		UPDATE_INFO+=" WHERE sl_no = ?;";
		
		
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INFO);){
			preparedStatement.setString(1, custInfo.getSl_no());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		}
	}
	//Search by Customer_Id
	public static Object[] custIdSearch(int start, int total, CustInfo custInfo,String sort,String order){
		
		List<CustInfo> custIDList = new ArrayList<CustInfo>();
		try {Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SEARCH_CUST+" ORDER BY "+sort+" "+order+" limit "+(start)+","+total);
				preparedStatement.setString(1, custInfo.getCust_number());
				System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					String sl_no = rs.getString("sl_no");
					String business_code = rs.getString("business_code");
					String cust_number = rs.getString("cust_number");
				    String clear_date = rs.getString("clear_date");
				    String buisness_year = rs.getString("buisness_year");
				    String doc_id = rs.getString("doc_id");
				    String posting_date = rs.getString("posting_date");
				    String document_create_date = rs.getString("document_create_date");
				    String document_create_date1 = rs.getString("document_create_date1");
				    String due_in_date = rs.getString("due_in_date");
				    String invoice_currency = rs.getString("invoice_currency");
				    String document_type = rs.getString("document_type"); 
				    String posting_id = rs.getString("posting_id");
				    String area_business = rs.getString("area_business");
				    String total_open_amount = rs.getString("total_open_amount");
				    String baseline_create_date = rs.getString("baseline_create_date");
				    String cust_payment_terms = rs.getString("cust_payment_terms");
				    String invoice_id = rs.getString("invoice_id");
				    String isOpen = rs.getString("isOpen");
				    String aging_bucket = rs.getString("aging_bucket");
				    String is_deleted = rs.getString("is_deleted");
				    custIDList.add(new CustInfo(sl_no, business_code, cust_number, clear_date, buisness_year, doc_id, posting_date, document_create_date, document_create_date1, due_in_date, invoice_currency, document_type, posting_id, area_business, total_open_amount, baseline_create_date, cust_payment_terms, invoice_id, isOpen, aging_bucket, is_deleted));
				}
				
		}catch(Exception e) {
			e.printStackTrace();
		}
		String rows = null;
		
		final String DISPLAY_ROWS = "SELECT COUNT(*) from winter_internship WHERE is_deleted = 0 AND cust_number = ?";
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DISPLAY_ROWS)){
			
			preparedStatement.setString(1, custInfo.getCust_number());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				rows = rs.getString("COUNT(*)");
				System.out.println(rows);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return new Object[]{rows,custIDList};
	}
	//Advance Search
	public static Object[] advSearch(int start, int total, CustInfo custInfo, String order, String sort){
		
		String ADV_SEARCH = SELECT_ADV_CUST;
		
		if(custInfo.getDoc_id() != "") {
			
			ADV_SEARCH = ADV_SEARCH+" AND doc_id = "+custInfo.getDoc_id();
		}
		
		if(custInfo.getInvoice_id() != "") {
			
			ADV_SEARCH = ADV_SEARCH+" AND invoice_id = "+custInfo.getInvoice_id();
		}
		
		if(custInfo.getCust_number() != "") {
			
			ADV_SEARCH =ADV_SEARCH+" AND cust_number = "+custInfo.getCust_number();
		}
		
		if(custInfo.getBuisness_year() != "") {
			
			ADV_SEARCH =ADV_SEARCH+" AND buisness_year = "+custInfo.getBuisness_year();
		}
		
		List<CustInfo> advList = new ArrayList<CustInfo>();
		try {Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(ADV_SEARCH+" ORDER BY "+sort+" "+order+" limit "+(start)+","+total);
				System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					String sl_no = rs.getString("sl_no");
					String business_code = rs.getString("business_code");
					String cust_number = rs.getString("cust_number");
				    String clear_date = rs.getString("clear_date");
				    String buisness_year = rs.getString("buisness_year");
				    String doc_id = rs.getString("doc_id");
				    String posting_date = rs.getString("posting_date");
				    String document_create_date = rs.getString("document_create_date");
				    String document_create_date1 = rs.getString("document_create_date1");
				    String due_in_date = rs.getString("due_in_date");
				    String invoice_currency = rs.getString("invoice_currency");
				    String document_type = rs.getString("document_type"); 
				    String posting_id = rs.getString("posting_id");
				    String area_business = rs.getString("area_business");
				    String total_open_amount = rs.getString("total_open_amount");
				    String baseline_create_date = rs.getString("baseline_create_date");
				    String cust_payment_terms = rs.getString("cust_payment_terms");
				    String invoice_id = rs.getString("invoice_id");
				    String isOpen = rs.getString("isOpen");
				    String aging_bucket = rs.getString("aging_bucket");
				    String is_deleted = rs.getString("is_deleted");
				    advList.add(new CustInfo(sl_no, business_code, cust_number, clear_date, buisness_year, doc_id, posting_date, document_create_date, document_create_date1, due_in_date, invoice_currency, document_type, posting_id, area_business, total_open_amount, baseline_create_date, cust_payment_terms, invoice_id, isOpen, aging_bucket, is_deleted));
				}
				
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String rows = null;
		
		String DISPLAY_ROWS = "SELECT COUNT(*) from winter_internship WHERE is_deleted = 0";
		
		if(custInfo.getDoc_id() != "") {
			
			DISPLAY_ROWS+=" AND doc_id = "+custInfo.getDoc_id();
		}
		
		if(custInfo.getInvoice_id() != "") {
			
			DISPLAY_ROWS+=" AND invoice_id = "+custInfo.getInvoice_id();
		}
		
		if(custInfo.getCust_number() != "") {
			
			DISPLAY_ROWS+=" AND cust_number = "+custInfo.getCust_number();
		}
		
		if(custInfo.getBuisness_year() != "") {
			
			DISPLAY_ROWS+=" AND buisness_year = "+custInfo.getBuisness_year();
		}
		
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DISPLAY_ROWS)){
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				rows = rs.getString("COUNT(*)");
				System.out.println(rows);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return new Object[]{rows,advList};
	}
	
	
	
	
	//Delete CustInfo
	public static void deleteUser(JSONArray sl_no) throws SQLException, JSONException {
		
		for(int i = 0; i<sl_no.length(); i++) {
			
			try(Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INFO);){
				Object sl = sl_no.get(i);
				preparedStatement.setString(1, (String) sl);
				System.out.println(preparedStatement);
				preparedStatement.executeUpdate();
			}
		}
	}
	
	//Analytics
	public static Object[] BarAnalytics(String invoice_cur,String clear_date1, String clear_date2, String baseline_ceate_date1, String baseline_ceate_date2, String due_in_date1, String due_in_date2) throws SQLException{
		
		String Max = "9999-12-31";
		String Min = "0000-00-00";
					
		ArrayList<String> TotalOpenAmount = new ArrayList<String>();
	    ArrayList<String> TotalCustomer = new ArrayList<String>();
	    ArrayList<String> BusinessCode = new ArrayList<String>();
	    ArrayList<String> InvoiceCurrency = new ArrayList<String>();
	    
	  
	    String ANALYTICS_DATA= "SELECT SUM(total_open_amount),COUNT(cust_number),business_code FROM winter_internship "+ANALYTICS+" GROUP BY business_code";

	    if(clear_date1=="") {clear_date1=Min;}
		
	    if(clear_date2=="") {clear_date2=Max;}
			
		if(baseline_ceate_date1=="") {baseline_ceate_date1=Min;}
			
	    if(baseline_ceate_date2=="") {baseline_ceate_date2=Max;}
		
	    if(due_in_date1=="") {due_in_date1=Min;}
		
	    if(due_in_date2=="") {due_in_date2=Max;}
			
		
	    try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(ANALYTICS_DATA)){
					
	    	preparedStatement.setString(1, clear_date1);
			preparedStatement.setString(2, clear_date2);
			preparedStatement.setString(3, baseline_ceate_date1);
			preparedStatement.setString(4, baseline_ceate_date2);
			preparedStatement.setString(5, due_in_date1);
			preparedStatement.setString(6, due_in_date2);
				
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();
	        while (rs.next())
			{
			
	        	String business_code=rs.getString("business_code");
				String cust_number=rs.getString("COUNT(cust_number)");
				String total_open_amount=rs.getString("SUM(total_open_amount)");
				
				BusinessCode.add(business_code);
				TotalCustomer.add(cust_number);
				TotalOpenAmount.add(total_open_amount);
				
			}
	    }
	    
	    String CURRENCY_ANALYTICS = CURRENCY;
	    
	    try(Connection connection = getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(CURRENCY_ANALYTICS)){
				
	    	preparedStatement.setString(1, clear_date1);
			preparedStatement.setString(2, clear_date2);
			preparedStatement.setString(3, baseline_ceate_date1);
			preparedStatement.setString(4, baseline_ceate_date2);
			preparedStatement.setString(5, due_in_date1);
			preparedStatement.setString(6, due_in_date2);
			
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next())
			{
			
				String cust_number=rs.getString("COUNT(invoice_currency)");
				
				InvoiceCurrency.add(cust_number);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	    
	    return new Object[]{BusinessCode,TotalCustomer,TotalOpenAmount,InvoiceCurrency};
	}
	
	//Predict Data
	public static List<CustInfo> getPredictData(JSONArray sl_number) throws SQLException, JSONException {
		
		
		List<CustInfo> predictList = new ArrayList<CustInfo>();
		String name_customer = null;
		for(int i = 0; i<sl_number.length(); i++) {
			
			try(Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(PREDICT_DATA);){
				Object sl = sl_number.get(i);
				preparedStatement.setString(1, (String) sl);
				System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					String business_code = rs.getString("business_code");
					switch (business_code) {
					case "CA02": 
						name_customer="Unilever";
						break;
					
					case "U001": 
						name_customer="Johnson and Johnson";
						break;
						
					case "U002": 
						name_customer="Bose";
						break;
						
					case "U005": 
						name_customer="Kellog's";
						break;
						
					case "U007": 
						name_customer="Sony";
						break;
						
					case "U013": 
						name_customer="Puma";
						break;
					}
					String cust_number = rs.getString("cust_number");
				    String clear_date = rs.getString("clear_date");
				    String buisness_year = rs.getString("buisness_year");
				    buisness_year = buisness_year.substring(0, 4);
				    String doc_id = rs.getString("doc_id");
				    String posting_date = rs.getString("posting_date");
				    String due_in_date = rs.getString("due_in_date");
				    Double total_amount = Double.parseDouble(rs.getString("total_open_amount"));
				    String baseline_create_date = rs.getString("baseline_create_date");
				    String cust_payment_terms = rs.getString("cust_payment_terms");
				    String invoice_currency = rs.getString("invoice_currency");
				    
				    switch (invoice_currency) {
					
					case "CAD": 
						
						total_amount=total_amount*0.79;
						break;
						
					}
				    
				    String coverted_usd = String.valueOf(total_amount);
				    predictList.add(new CustInfo( business_code, cust_number , name_customer, clear_date, buisness_year, doc_id, posting_date, due_in_date, baseline_create_date, cust_payment_terms, coverted_usd));
				}
			}
		}
		return predictList;
	}
	
	//Setting Aging Bucket
	public void setAgingBucket(CustInfo custInfo) throws SQLException
	{
		
		
		try(Connection connection = getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(AGING_BUCKET)){
				
				
				preparedStatement.setString(1, custInfo.getAging_bucket());
				preparedStatement.setString(2, custInfo.getDoc_id());
				
				System.out.println(preparedStatement);
				
				preparedStatement.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
