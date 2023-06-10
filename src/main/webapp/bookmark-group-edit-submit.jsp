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
	String markId = request.getParameter("markId");
	String markName = request.getParameter("markName");
	String markOrder = request.getParameter("markOrder");
	
	Service svc = new Service();
	int result = svc.updateG(markId, markName, markOrder);
	if (result == 1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('북마크 그룹 정보를 수정하였습니다.')");
		script.println("location.href = 'bookmark-group.jsp'");
		script.println("</script>");
	} else if (result == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('북마크 그룹 정보가 수정되지 않았습니다.')");
		script.println("history.back()");
		script.println("</script>");
	}
%>
</body>
</html>