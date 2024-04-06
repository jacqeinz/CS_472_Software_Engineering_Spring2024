<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="guest" method="post">
  <div class="elem-group">
    <label for="name">Your Name</label>
    <input type="text" id="name" name="guest" placeholder="John Doe" pattern=[A-Z\sa-z]{3,20} required>
  </div>
  <div class="elem-group">
    <label for="eid">Employee ID</label>
    <input type="text" id="eid" name="eID" placeholder="EmployeeID"  required>
  </div>
  <div class="elem-group">
    <label for="email">Your E-mail</label>
    <input type="email" id="email" name="email" placeholder="john.doe@email.com" required>
  </div>
  <div class="elem-group">
    <label for="phone">Your Phone</label>
    <input type="tel" id="phone" name="phone_number" placeholder="498-348-3872" pattern=(\d{3})-?\s?(\d{3})-?\s?(\d{4}) required>
  </div>
  <div class="elem-group">
    <label for="name">DOB</label>
    <input type="text" id="name" name="dob" placeholder="MM/DD/YYYY" pattern=[A-Z\sa-z]{3,20} required>
  </div>
  <div class="elem-group">
    <label for="email">Your E-mail</label>
    <input type="email" id="email" name="email" placeholder="john.doe@email.com" required>
  </div>
  <div class="elem-group">
    <label for="phone">User Name</label>
    <input type="tel" id="phone" name="user_name"  required>
  </div>
  <div class="elem-group">
    <label for="phone">User Password</label>
    <input type="tel" id="phone" name="user_password" required>
  </div>
  <button type="submit">Register</button>
</form>
</body>
</html>