<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Item</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<jsp:include page="templateBeforeContent.jsp" />
		
		<br/><h2>Add Item</h2><br/>
		
		<script>document.write(helpBox("Enter the details of the new Item"));</script>
		
		<p class="error">${errorMessage}</p>
		
		<form:form  method="post" onsubmit="return true" action="addItem.html" commandName="item" id="addItemForm">
			<table>
			    <tr>
			        <td><form:label path="title">Title:</form:label></td>
			        <td><form:input path="title" id="addItemTitle" /></td>
			    </tr>
			    <tr>
			        <td><form:label path="author">Author:</form:label></td>
			        <td><form:input path="author" id="addItemAuthor" /></td>
			    </tr>
			    <tr>
			        <td><form:label path="edition">Edition:</form:label></td>
			        <td><form:input path="edition" id="addItemEdition" /></td>
			    </tr>
			    <tr>
			        <td><form:label path="publishDate">Publish Date:</form:label></td>
			        <td><form:input path="publishDate" id="addItemDate" /></td>
			    </tr>
			    <tr>
			        <td colspan="2">
			        	<br/>
			            <input type="submit" value="Add Item" id="addItemButton" class="submitButton"/>
			        </td>
			    </tr>
			</table>
		</form:form>

		<jsp:include page="templateAfterContent.jsp" /> 

	</body>
</html>