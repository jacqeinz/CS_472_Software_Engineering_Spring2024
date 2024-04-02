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
<title>Home</title>
</head>
<body>
<header>
        <h1>Welcome to our Hotel Reservation System</h1>
  
    </header>

  <div class="topleft">
    <img src= "images/logo.PNG">
    <hr>
   </div>
         <div class="topright">
        <button onclick="window.location.href = 'Reservation.jsp'">Make Reservation </button>
      </div>
    
      
   </body>

<style type = "text/css">

body, html {
  height: 100%
}



/* Position text in the top-left corner */
.topleft {
  position: absolute;
  top: 0;
  left: 16px;
  max-width: 15%;
  max-height: 15%
}
.topright {
  position: absolute;
  top: 8px;
  right: 16px;
  font-size: 18px;
}

/* Position text in the bottom-left corner */
.bottomleft {
  position: absolute;
  bottom: 0;
  left: 16px;
}

/* Position text in the middle */
.middle {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

/* Style the <hr> element */
hr {
  margin: auto;
  width: 40%;
}

</style>
</html>