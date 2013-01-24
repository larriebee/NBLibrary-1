<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>List Overdue Loans</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<jsp:include page="templateBeforeContent.jsp" />
		
		<c:if  test="${!empty loans}">
			<form  method="get" action="listLoansSubmission.html">
			<table>
				<thead>
					<tr><td colspan="7"><h2>List of Overdue Loans</h2></td></tr>
				</thead>
				<tbody>
				<tr>
					<th>Selection</th>
    				<th>Loan Id</th>
    				<th>Start Date</th>
    				<th>End Date</th>
    				<th>Open</th>
    				<th>Overdue</th>
    				<th>Borrower</th>
    				<th>Item</th>
    			</tr>
				<c:forEach items="${loans}" var="loan" varStatus="status">
					<tr>
						<td class="checkbox"><input type="checkbox" name="loanSelection" value="${loan.loanId}" class="checkbox"/></td>
						<td>${loan.loanId}</td>
						<td><fmt:formatDate value="${loan.startDate}" pattern="d MMMM yyyy"/></td>
						<td><fmt:formatDate value="${loan.endDate}" pattern="d MMMM yyyy"/></td> 
						<td><input type="checkbox" disabled="disabled" ${loan.open ? 'checked="checked"' : ''} class="checkbox"/></td>
						<td><input type="checkbox" disabled="disabled" ${loan.overdue ? 'checked="checked"' : ''} class="checkbox"/></td>
						<td>${borrowers[status.count-1].name}</td>
						<td>${items[status.count-1].title}</td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
				        <td colspan="6">
				           	<input type="submit" name="buttonSelected" value="Return Item" class="submitButton"/>
				        </td>
				        <td colspan="2">
				           	<input type="submit" name="buttonSelected" value="Sign Out Item" class="submitButton"/>
				        </td>
				    </tr>
				</tfoot>
			</table>
			</form>
		</c:if>	
		<c:if  test="${empty loans}">
		
			<h2>You currently have no overdue Loans</h2>
			<form method="get" action="listLoansSubmission.html">
				<input type="submit" name="buttonSelected" value="Sign Out Item" class="submitButton"/>
			</form>
		</c:if>
		
		<p class="error">${errorMessage}</p>
		
		<jsp:include page="templateAfterContent.jsp" /> 

	</body>
</html>