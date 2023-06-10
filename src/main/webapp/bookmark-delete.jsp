<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.Service" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style>
#table {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#table td, #table th {
  border: 1px solid #ddd;
  padding: 8px;
}

#table tr:nth-child(even){background-color: #f2f2f2;}

#table tr:hover {background-color: #ddd;}

#table th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: center;
  background-color: #04AA6D;
  color: white;
}
</style>
</head>
<body>
<%
	String markId = request.getParameter("markId");
	String wifiId = request.getParameter("wifiId");
	
	Service svc = new Service();
	String[] mark = svc.selectM(markId, wifiId);
%>
<h2>북마크 그룹</h2>
<p> <a href="wifi-info.jsp">홈</a> | <a href="search-history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></p>

<p>북마크를 삭제하시겠습니까?</p>
<table id="table">
	<tr>
		<th>북마크 이름</th>
		<td><%=mark[0] %></td>
	</tr>
	<tr>
		<th>와이파이명</th>
		<td><%=mark[1] %></td>
	</tr>
	<tr>
		<th>등록일자</th>
		<td><%=mark[2] %></td>
	</tr>
</table>
<form action="bookmark-delete-submit.jsp">
<p align="center"><a href="bookmark-list.jsp">돌아가기</a> | <button type="submit" name="id" value="<%=mark[3] %>">삭제</button>
</form>
</body>
</html>