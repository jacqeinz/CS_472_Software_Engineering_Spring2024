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
<title>Shopping Cart</title>
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

<script type="text/javascript">
function Test(cart)
{
	 if(cart>0){
		 window.location.href='Confirmation.jsp'
	 }
	 else{
		 alert("Your cart is Empty!")
	 }
}
</script>

</head>

<body>
	<%
	String CheckIn = "2024-03-28";
	String CheckOut = "2024-03-30";
	Cart cart = new Cart("$500", CheckIn, CheckOut);
	cart.setDetailsGuest("Team Abcfg");
	%>
	<div class="header">
		<h1>ABCFG Hotel</h1>
	</div>
	<table>
		<tr>
			<th>Guest</th>
			<th>Room</th>
			<th>Check-In Date</th>
			<th>Check-Out Date</th>
			<th>Price</th>
		</tr>
		<tr>
			<td><%=cart.getDetailsGuest()%></td>
			<td>Single Queen Sized Bed</td>
			<td><%=cart.getCheckIn()%></td>
			<td><%=cart.getCheckOut()%></td>
			<td><%=cart.getPrice()%></td>
		</tr>
		<%

		%>
	</table>
	<button onclick="Test(<%=cart.getCartSize()%>)">Checkout</button>
</body>
</html>