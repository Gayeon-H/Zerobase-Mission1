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
	String wifiId = request.getParameter("wifiId");
	
	Service svc = new Service();
	
	if ("0".equals(markId)) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('북마크 그룹을 다시 선택해주세요.')");
		script.println("history.back()");
		script.println("</script>");
	} else {
		int result = svc.insertM(markId, wifiId);
		
		if (result == 1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('북마크 정보를 추가하였습니다.')");
			script.println("location.href = 'bookmark-list.jsp'");
			script.println("</script>");
		} else if (result == 0) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('북마크 정보가 추가되지 않았습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
	}
%>
</body>
</html>