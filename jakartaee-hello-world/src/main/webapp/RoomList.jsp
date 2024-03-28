<%@page import="java.util.*"%>
<%@page import="org.eclipse.jakarta.model.entity.Cart"%>
<%@page import="org.eclipse.jakarta.model.entity.Reservation"%>
<%@page import="org.eclipse.jakarta.model.entity.RoomReservation"%>
<%@page import="org.eclipse.jakarta.model.entity.ReservationDetails"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Room Options</title>
<style>
.header {
	padding: 10px;
	text-align: center;
	background: #04AA6D;
	color: white;
	font-size: 30px;
}

table {
	width: 100%;
	margin-top: 15px;
	margin-left: auto;
	margin-right: auto;
	border: 1px solid black;
	padding: 10px;
}

td {
	text-align: center;
}

button {
	background-color: #04AA6D;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
}
</style>



</head>

<body>
	<%
	String CheckIn = "2024-02-19";
	String CheckOut = "2024-02-24";
	String roomType = "Single Queen Sized Bed";
	String price = "500";
	%>
	<div class="header">
		<h1>ABCFG Hotel</h1>
	</div>
	<table>
		<tr>
			<th>Room</th>
			<th>Price</th>
			
			<th>Check-In Date</th>
			<th>Check-Out Date</th>
			
		</tr>
		<tr>
			<td><%=(roomType)%></td>
			<td><%=(price)%></td>
			
			<td><%=(CheckIn)%></td>
			<td><%=(CheckOut)%></td>
			
		</tr>
		<%

		%>
	</table>
	<button onclick="window.location.href = 'Cart.jsp'">Add to Cart </button>
</body>
</html>