<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.Service" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<body>
<%
	String hisId = request.getParameter("hisId");
	
	Service svc = new Service();
	int result = svc.deleteH(hisId);
	if (result == 1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('히스토리를 삭제하였습니다.')");
		script.println("location.href = 'search-history.jsp'");
		script.println("</script>");
	} else if (result == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('히스토리가 삭제되지 않았습니다.')");
		script.println("history.back()");
		script.println("</script>");
	}
%>
</body>
</html>