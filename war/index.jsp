<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.yuktix.DataHolder,com.yuktix.StationObject" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bengaluru Weather</title>
<style>
b {
 color: red;
 text-align: center;
}
table {
    border: 1px solid black;
    margin-left: auto;
    margin-right: auto;
}
th, td {
padding: 5px;
border-bottom: 1px solid #ddd;
}
th {
    height: 50px;
}
</style>
</head>
<body>
<table>
<tr>
<th>Station</th>
<th>Max</th>
<!-- <th>Time at which the max was recorded</th> -->
<th>Min</th>
<!-- <th>Time at which the min was recorded</th>-->
<th>Rainfall</th>
<th>Current temperature</th>
<th>Relative Humidity</th>
</tr>
<%
for(StationObject station:DataHolder.stationdata) { %>
<tr>
<td><%=station.name%></td>
<td><%=station.max%></td>
<td><%=station.min%></td>
<td><%=station.rainfall%></td>
<td><%=station.temp%></td>
<td><%=station.rh%>
</tr>
<%} %>
</table>
<b>Current Temperature and Relative Humidity last updated at <%=DataHolder.lastupdated%></b>
<b>Max and min temperatures updated at 5:30 pm IST and 8:30 pm IST respectively</b>
</body>
</html>