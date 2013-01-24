<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>List Items</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<jsp:include page="templateBeforeContent.jsp" />
		
		<c:if  test="${!empty items}">
			<form  method="get" action="listItemsSubmission.html">
			<table>
				<thead>
					<tr><td colspan="6"><h2>List of Items</h2></td></tr>
				</thead>
				<tbody>
				<tr>
					<th>Selection</th>
    				<th>Item Id</th>
    				<th>Title</th>
    				<th>Author</th>
    				<th>Edition</th>
    				<th>Publish Date</th>
    				<th>Loanable</th>
    			</tr>
				<c:forEach items="${items}" var="item">
					<tr>
						<td class="checkbox"><input type="radio" name="itemSelection" value="${item.itemId}" class="checkbox"/></td>
						<td>${item.itemId}</td>
						<td>${item.title}</td>
						<td>${item.author}</td>
						<td>${item.edition}</td>
						<td>${item.publishDate}</td>
						<td><input type="checkbox" disabled="disabled" ${item.loanable ? 'checked="checked"' : ''} class="checkbox"/></td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
				        <td colspan="2">
				           	<input type="submit" name="buttonSelected" value="Update Item" class="submitButton"/>
				        </td>
				        <td colspan="3">
				           	<input type="submit" name="buttonSelected" value="Remove Item" class="submitButton"/>
				        </td>
				        <td colspan="2">
				           	<input type="submit" name="buttonSelected" value="Add Item" class="submitButton"/>
				        </td>
				    </tr>
				</tfoot>
			</table>
			</form>
		</c:if>	
		<c:if  test="${empty items}">
		
			<h2>You currently have no Items</h2>
		
		</c:if>

		<p class="error">${errorMessage}</p>

		<jsp:include page="templateAfterContent.jsp" /> 

	</body>
</html>