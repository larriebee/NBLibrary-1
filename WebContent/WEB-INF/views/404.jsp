<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Page Could Not Be Found</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<jsp:include page="templateBeforeContent.jsp" />
		
		<br/><br/><br/><br/><br/><br/><br/><br/><h1>404: Page Not Found</h1><br/>
		<p>Sorry but the page you are looking for has not been found. 
		<br/>Try checking the URL for errors or <a href="index.html">click here to return to the home page.</a></p>

		<jsp:include page="templateAfterContent.jsp" /> 

	</body>
</html>