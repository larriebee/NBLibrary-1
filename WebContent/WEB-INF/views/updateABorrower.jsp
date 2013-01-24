<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Update Borrower</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<jsp:include page="templateBeforeContent.jsp" />
		
		<br/><h2>Update Borrower</h2><br/>
		
		<script>document.write(helpBox("Enter the Borrower's new details"));</script><br/>
		
		<form:form  method="post" action="updateBorrower.html" commandName="borrower">
			<table>
			    <tr>
			        <td><form:label path="name">Name:</form:label></td>
			        <td><form:input path="name" value="${borrower.name}" /></td>
			    </tr>
			    <tr>
			        <td><form:label path="email">Email:</form:label></td>
			        <td><form:input path="email" value="${borrower.email}" /></td>
			    </tr>
			    <tr>
			        <td><form:label path="strikes">Strikes:</form:label></td>
			        <td><form:input id="amount" path="strikes" value="${borrower.strikes}" />
			        <div id="slider" style="display:inline-block; width:5em" ></div></td>
			    </tr>
			    <tr>
			        <td colspan="2">
			            <input type="submit" value="Update Borrower" class="submitButton"/>
			        </td>
			    </tr>
			</table>
		</form:form>

		<jsp:include page="templateAfterContent.jsp" /> 

	</body>
</html>