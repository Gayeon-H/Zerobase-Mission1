<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.Wifi"%>
<%@ page import="data.Service"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
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
	String wifiId = request.getParameter("wifiId");
	
	Service svc = new Service();
	Wifi wifi = svc.showDetail(wifiId);
	List<String[]> list = svc.selectG();
%>
<h2>와이파이 정보 구하기</h2>
<p> <a href="wifi-info.jsp">홈</a> | <a href="search-history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></p>
<form method="get" action="bookmark-add-submit.jsp"> 
	  <select name="markId">
	  	<option value="0" selected>북마크 그룹 이름 선택</option>
	  	<%
	  	if (list.size() != 0) {
	  		for (String[] s : list) {
	  	%>
	  			<option value="<%=s[0] %>"><%=s[1] %></option>
	  	<%
	  		}
	  	}
	  	%>
	  </select>
	  <button type="submit" name="wifiId" value="<%=wifiId %>">북마크 추가하기</button>
	  </form>

<table id="table">
	<colgroup>
		<col style="width: 30%;" />
		<col style="width: 70%;" />
	</colgroup>
	<tbody>
		<tr>
			<th>거리(Km)</th>
			<td><%=wifi.getDistance() %></td>
		</tr>
		<tr>
			<th>관리번호</th>
			<td><%=wifi.getWifiId() %></td>
		</tr>
		<tr>
			<th>자치구</th>
			<td><%=wifi.getWrdofc() %></td>
		</tr>
		<tr>
			<th>와이파이명</th>
			<td><a href="wifi-detail.jsp?wifiId=<%=wifiId %>">
				<%=wifi.getWifiName() %></a></td>
		</tr>
		<tr>
			<th>도로명주소</th>
			<td><%=wifi.getAdres1() %></td>
		</tr>
		<tr>
			<th>상세주소</th>
			<td><%=wifi.getAdres2() %></td>
		</tr>
		<tr>
			<th>설치위치(층)</th>
			<td><%=wifi.getFloor() %></td>
		</tr>
		<tr>
			<th>설치유형</th>
			<td><%=wifi.getInstlTy() %></td>
		</tr>
		<tr>
			<th>설치기관</th>
			<td><%=wifi.getInstlMby() %></td>
		</tr>
		<tr>
			<th>서비스구분</th>
			<td><%=wifi.getSvcSe() %></td>
		</tr>
		<tr>
			<th>망종류</th>
			<td><%=wifi.getCmcwr() %></td>
		</tr>
		<tr>
			<th>설치년도</th>
			<td><%=wifi.getCnstcYear() %></td>
		</tr>
		<tr>
			<th>실내외구분</th>
			<td><%=wifi.getInOut() %></td>
		</tr>
		<tr>
			<th>WIFI접속환경</th>
			<td><%=wifi.getRemars3() %></td>
		</tr>
		<tr>
			<th>X좌표</th>
			<td><%=wifi.getLat() %></td>
		</tr>
		<tr>
			<th>Y좌표</th>
			<td><%=wifi.getLnt() %></td>
		</tr>
		<tr>
			<th>작업일자</th>
			<td><%=wifi.getWorkDt() %></td>
		</tr>
	</tbody>
</table>
</body>
</html>