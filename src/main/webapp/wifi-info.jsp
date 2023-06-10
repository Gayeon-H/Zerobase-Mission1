<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.Wifi"%>
<%@ page import="data.Service"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="wifi" class="data.Wifi" scope="page" />
<jsp:setProperty name="wifi" property="lat" />
<jsp:setProperty name="wifi" property="lnt" />

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
<script>
function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
  } else {
    alert("Geolocation is not supported by this browser.");
  }
}

function showPosition(position) {
	document.getElementById("lat").value = position.coords.latitude;
	document.getElementById("lnt").value = position.coords.longitude;
}
</script>
<%
	Service svc = new Service();
	if (wifi.getLat() != 0.0 && wifi.getLnt() != 0.0) {
		svc.insertH(wifi.getLat(), wifi.getLnt());
	}
	List<Wifi> info = svc.showList(wifi.getLat(), wifi.getLnt());
	
%>
<h2>와이파이 정보 구하기</h2>
<p> <a href="wifi-info.jsp">홈</a> | <a href="search-history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></p>
<form method="get" action="wifi-info.jsp"> 
	  LAT: <input type="text" id="lat" name="lat" value="<%=wifi.getLat() %>" required>, LNT: <input type="text" id="lnt" name="lnt" value="<%=wifi.getLnt() %>" required> 
	  <button type="button" onclick="getLocation()">내 위치 가져오기</button> 
	  <button type="submit">근처 WIFI 정보 보기</button>
	  </form>

<table id="table">
	<tr>
		<th>거리(Km)</th>
		<th>관리번호</th>
		<th>자치구</th>
		<th>와이파이명</th>
		<th>도로명주소</th>
		<th>상세주소</th>
		<th>설치위치(층)</th>
		<th>설치유형</th>
		<th>설치기관</th>
		<th>서비스구분</th>
		<th>망종류</th>
		<th>설치년도</th>
		<th>실내외구분</th>
		<th>WIFI접속환경</th>
		<th>X좌표</th>
		<th>Y좌표</th>
		<th>작업일자</th>
	</tr>
	<%
	if (wifi.getLat() == 0.0 || wifi.getLnt() == 0.0) {
	%>
		<tr>
			<td colspan="17" align="center">위치 정보를 입력한 후에 조회해 주세요.</td>
		</tr>
	<%	
	} else {
	for (Wifi w : info) {
		String s = String.format("%.4f", w.getDistance());
	%>
		<tr>
			<td><%=s %></td>
			<td><%=w.getWifiId() %></td>
			<td><%=w.getWrdofc() %></td>
			<td><a href="wifi-detail.jsp?wifiId=<%=w.getWifiId() %>">
				<%=w.getWifiName() %></a></td>
			<td><%=w.getAdres1() %></td>
			<td><%=w.getAdres2() %></td>
			<td><%=w.getFloor() %></td>
			<td><%=w.getInstlTy() %></td>
			<td><%=w.getInstlMby() %></td>
			<td><%=w.getSvcSe() %></td>
			<td><%=w.getCmcwr() %></td>
			<td><%=w.getCnstcYear() %></td>
			<td><%=w.getInOut() %></td>
			<td><%=w.getRemars3() %></td>
			<td><%=w.getLat() %></td>
			<td><%=w.getLnt() %></td>
			<td><%=w.getWorkDt() %></td>
		</tr>
	<%
		}
	}	
	%>
</table>
</body>
</html>