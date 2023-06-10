<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.Load"%>
<%@ page import="java.io.IOException"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style>
body {text-align: center};
</style>
</head>
<body>
<%
	Load load = new Load();
	try {
		load.run();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	int cnt = load.getCount();

%>
<h2><%=cnt %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h2>
<a href="wifi-info.jsp">홈으로 가기</a>
</body>
</html>