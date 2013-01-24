<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ include file="taglibs.jsp"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Borrower</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<jsp:include page="templateBeforeContent.jsp" />

		<br/><h2>Add Borrower</h2><br/>

		<script>document.write(helpBox("Enter the details of the new Borrower"));</script>
		
		<p class="error">${errorMessage}</p>
		
		<form:form  method="post" onsubmit="return true" action="addBorrower.html" commandName="borrower" id="addBorrowerForm">
			<table>
			    <tr>
			        <td><form:label path="name">Name:</form:label></td>
			        <td><form:input path="name" id="addBorrowerName" /></td>
			    </tr>
			    <tr>
			        <td><form:label path="email">Email:</form:label></td>
			        <td><form:input path="email" id="addBorrowerEmail"/></td>
			    </tr>
			    <tr>
			        <td colspan="2">
			        	<br/>
			            <input type="submit" value="Add Borrower" id="addBorrowerButton" class="submitButton"/>
			        </td>
			    </tr>
			</table>
		</form:form>
		
		<jsp:include page="templateAfterContent.jsp" />

	</body>
</html>