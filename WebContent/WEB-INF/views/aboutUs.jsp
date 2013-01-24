<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>About Us</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<jsp:include page="templateBeforeContent.jsp" />
		
		<img src="<c:url value="resources/images/logo.png"  />" alt="">
		
		<br/><br/><br/>
		<h3>Development</h3>
		<p>Matthew Beck<br/>Victor Yusuf</p>
		
		<h3>Design</h3>
		<p>Matthew Beck<br/>Victor Yusuf</p>
		
		<h3>Executive Consultant</h3>
		<p>Edward Luke</p>
		
		<h3>Special Thanks</h3>
		<p>Paul Holiday<br/>Vladimir Weedon</p>

		<jsp:include page="templateAfterContent.jsp" /> 

	</body>
</html>