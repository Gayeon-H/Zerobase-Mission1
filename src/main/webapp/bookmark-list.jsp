<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.Service" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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

#table tr:nth-child(odd){background-color: #f2f2f2;}

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
	Service svc = new Service();
	List<String[]> list = svc.selectM();
%>
<h2>북마크 목록</h2>
<p> <a href="wifi-info.jsp">홈</a> | <a href="search-history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></p>
<table id="table">
	<tr>
		<th>ID</th>
		<th>북마크 이름</th>
		<th>와이파이명</th>
		<th>등록일자</th>
		<th>비고</th>
	</tr>
	<%
	if (list.size() == 0) {
	%>
		<tr>
			<td colspan="5" align="center">정보가 존재하지 않습니다.</td>
		</tr>
	
	<%
	}
	
	for (String[] s: list) {
	%>
	<tr>
		<td><%=s[0] %></td>
		<td><%=s[1] %></td>
		<td><a href="wifi-detail.jsp?wifiId=<%=s[5] %>"><%=s[2] %></a></td>
		<td><%=s[3] %></td>
		<td align="center"><a href="bookmark-delete.jsp?markId=<%=s[4] %>&wifiId=<%=s[5] %>">삭제</a></td>
	</tr>
	<%
	}
	%>
</table>
</body>
</html>