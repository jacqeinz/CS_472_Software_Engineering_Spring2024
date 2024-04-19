<%@ page import="java.util.List" %>
<%@ page import="org.eclipse.jakarta.model.entity.Room" %>
<%@ page import="org.eclipse.jakarta.model.entity.RoomReservation" %>
<%@ page import="org.eclipse.jakarta.model.entity.GraphicalView" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Floor Plan</title>
    <style>
        /* CSS styles for the hotel floor plan */

        /* Style for the main container */
        .floor-plan {
            margin: 20px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
        }

        /* Style for the room boxes */
        .room-box {
            width: 100px;
            height: 100px;
            margin: 5px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #fff;
            display: inline-block;
            text-align: center;
            line-height: 100px; /* Center text vertically */
        }

        /* Style for available rooms */
        .available-room {
            background-color: #b3ffb3; /* Light green */
        }

        /* Style for booked rooms */
        .booked-room {
            background-color: #ff9999; /* Light red */
        }

        /* Style for the room number */
        .room-number {
            font-weight: bold;
        }

        /* Style for the reservation information */
        .reservation-info {
            font-size: 12px;
        }
    </style>
</head>
<body>
    <h2>Hotel Floor Plan</h2>
    <div class="floor-plan">
        <%-- Get rooms and reservations from request attributes --%>
        <% List<Room> rooms = (List<Room>) request.getAttribute("rooms"); %>
        <% List<RoomReservation> reservations = (List<RoomReservation>) request.getAttribute("reservations"); %>
        
        <%-- Create an instance of GraphicalView --%>
        <% GraphicalView graphicalView = new GraphicalView(); %>
        
        <%-- Generate HTML content for the hotel floor plan using GraphicalView --%>
        <%= graphicalView.generateHotelFloorPlanHTML(rooms, reservations) %>
    </div>
</body>
</html>

