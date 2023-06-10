<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<h2>북마크 그룹 추가</h2>
<p> <a href="wifi-info.jsp">홈</a> | <a href="search-history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></p>
<form action="bookmark-group-add-submit.jsp">
<table id="table">
	<tr>
		<th>북마크 이름</th>
		<td><input type="text" name="markName" required></td>
	</tr>
	<tr>
		<th>순서</th>
		<td><input type="text" name="markOrder" required></td>
	</tr>
</table>
<p align="center"><button type="submit">추가</button></p>
</form>
</body>
</html>