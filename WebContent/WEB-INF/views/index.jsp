<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<jsp:include page="templateBeforeContent.jsp" />
			
		<!--form:form method="POST" action="/HelloForms/addStudent"-->
   
			<table>
				<tr>
					<td><label><h3>Search for Books</h3></label><br/></td>
				</tr>
				<tr>
					<td><input type="text" value="Enter Search Criteria..." 
						   onfocus="if(this.value == this.defaultValue) this.value = ''" size="100"/></td>
				</tr>
			    <tr>
					<td>
				    	<input name="search_type" type="radio" path="id" /> By Title
				    </td>
				</tr>
				<tr>
					<td>
					    <input name="search_type" type="radio" path="id" /> By Author
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="Search" class="submitButton"/>
					</td>
				</tr>
			    <tr>
				    <td colspan="2"></td>
				</tr>
				
			</table>  
		<!--/form:form-->
            
		<br/><br/>
		
		<jsp:include page="templateAfterContent.jsp" /> 

	</body>
</html>