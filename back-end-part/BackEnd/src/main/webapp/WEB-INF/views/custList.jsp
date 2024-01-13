<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
<table>
  <thead>
    <tr>
      <th>Sl No</th>
      <th>Business Code</th>
      <th>Customer Number</th>
      <th>Clear Date</th>
      <th>Business Year</th>
      <th>Document Id</th>
      <th>Posting Date</th>
      <th>Document Create Date</th>
      <th>Due Date</th>
      <th>Invoice Currency</th>
      <th>Document Type</th>
      <th>Posting Id</th>
      <th>Area Business</th>
      <th>Total Open Amount</th>
      <th>Baseline Create Date</th>
      <th>Cust Payment Terms</th>
      <th>Invoice ID</th>
      <th>Is Open</th>
      <th>Predicted</th>
    </tr>
  </thead>
  <tbody>
  	<c:forEach var="invoice" items="${listCust}">
    	<tr>
      		<td><c:out value="${invoice.sl_no}" /></td>
 			<td><c:out value="${invoice.business_code}" /></td>
 			<td><c:out value="${invoice.cust_number}" /></td>
 			<td><c:out value="${invoice.clear_date}" /></td>
 			<td><c:out value="${invoice.buisness_year}" /></td>
 			<td><c:out value="${invoice.doc_id}" /></td>
 			<td><c:out value="${invoice.posting_date}" /></td>
 			<td><c:out value="${invoice.document_create_date1}" /></td>
 			<td><c:out value="${invoice.due_in_date}" /></td>
 			<td><c:out value="${invoice.invoice_currency}" /></td>
 			<td><c:out value="${invoice.document_type}" /></td>
 			<td><c:out value="${invoice.posting_id}" /></td>
 			<td><c:out value="${invoice.area_business}" /></td>
 			<td><c:out value="${invoice.total_open_amount}" /></td>
 			<td><c:out value="${invoice.baseline_create_date}" /></td>
 			<td><c:out value="${invoice.cust_payment_terms}" /></td>
 			<td><c:out value="${invoice.invoice_id}" /></td>
 			<td><c:out value="${invoice.isOpen}" /></td>
 			<td><c:out value="${invoice.aging_bucket}" /></td>
    	</tr>
    </c:forEach>
  </tbody>
</table>
			<div class="container text-left">

				<a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add
					New User</a>
			</div>
</body>
</html>