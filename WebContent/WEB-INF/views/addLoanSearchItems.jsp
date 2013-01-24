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
		
		<script>document.write(helpBox("Enter the title of the Item to sign out"));</script>
		
		<p class="error">${errorMessage}</p>
		
		<h3>Borrower Name: ${borrower.name}</h3>
		
		<form:form  method="get" action="addLoanConfirmation.html" commandName="item">
			<table>
			    <tr>
			        <td><label>Item Title:</label></td>
			        <td><input type="text" name="title" class="auto_complete_title"></td>
			    </tr>
			    <tr>
			        <td colspan="2">
			            <input type="submit" value="Select Item" class="submitButton"/>
			        </td>
			    </tr>
			</table>
		</form:form>


		<jsp:include page="templateAfterContent.jsp" /> 

	</body>
</html>