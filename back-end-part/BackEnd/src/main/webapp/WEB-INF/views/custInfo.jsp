<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <form action="<%= request.getContextPath() %>/insert" method="post">
        <input type="number" name="sl_no" placeholder="sl_no" />
        <input type="text" name="business_code" placeholder="business_code" />
        <input type="number" name="cust_number" placeholder="cust_number" />
        <input type="date" name="clear_date" placeholder="clear_date" />
        <input type="number" name="buisness_year" placeholder="buisness_year" />
        <input type="text" name="doc_id" placeholder="doc_id" />
        <input type="date" name="posting_date" placeholder="posting_date" />
        <input type="date" name="document_create_date" placeholder="document_create_date" />
        <input type="date" name="document_create_date1" placeholder="document_create_date1" />
        <input type="date" name="due_in_date" placeholder="due_in_date" />
        <input type="text" name="invoice_currency" placeholder="invoice_currency" />
        <input type="text" name="document_type" placeholder="document_type" />
        <input type="number" name="posting_id" placeholder="posting_id" />
        <input type="text" name="area_business" placeholder="area_business" />
        <input type="number" name="total_open_amount" placeholder="total_open_amount" />
        <input type="date" name="baseline_ceate_date" placeholder="baseline_ceate_date" />
        <input type="text" name="cust_payment_terms" placeholder="cust_payment_terms" />
        <input type="number" name="invoice_id" placeholder="invoice_id" />
        <input type="number" name="isOpen" placeholder="isOpen" />
        <input type="text" name="aging_bucket" placeholder="aging_bucket" />
        <input type="number" name="is_deleted" placeholder="is_deleted" />
        <button type="submit">Submit</button>
    </form>
</body>
</html>