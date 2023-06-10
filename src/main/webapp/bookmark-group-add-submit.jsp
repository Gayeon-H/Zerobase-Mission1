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
	String markName = request.getParameter("markName");
	String markOrder = request.getParameter("markOrder");	
	
	Service svc = new Service();
	int result = svc.insertG(markName, markOrder);
	if (result == 1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('북마크 그룹 정보를 추가하였습니다.')");
		script.println("location.href = 'bookmark-group.jsp'");
		script.println("</script>");
	} else if (result == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('북마크 그룹 정보가 추가되지 않았습니다. 그룹 이름이나 순서가 올바른지 확인해주세요')");
		script.println("history.back()");
		script.println("</script>");
	}
%>
</body>
</html>