<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Loan</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<jsp:include page="templateBeforeContent.jsp" />
		
		<br/><h2>Sign Out Item</h2><br/>
		
		<script>document.write(helpBox("Enter the email of the Borrower to sign the Item out to"));</script>
		
		<p class="error">${errorMessage}</p>
		
		<form:form  method="get" action="addLoanItem.html" commandName="borrower">
			<table>
			    <tr>
			        <td><label>Borrower Email:</label></td>
			        <td><input type="text" name="email" class="auto_complete_email"></td>
			    </tr>
			    <tr>
			        <td colspan="2">
			            <input type="submit" value="Select Borrower" class="submitButton"/>
			        </td>
			    </tr>
			</table>
		</form:form>

		<jsp:include page="templateAfterContent.jsp" /> 

	</body>
</html>