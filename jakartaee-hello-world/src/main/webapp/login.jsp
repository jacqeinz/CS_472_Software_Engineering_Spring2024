<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Application</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
 
<!-- css related code which we can have either in 
     same jsp or separately also in a css file -->
<style>
body {font-family: Arial, Helvetica, sans-serif;}
form {border: 3px solid #f1f1f1;}
 
input[type=text], input[type=password] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
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
 
button:hover {
  opacity: 0.8;
}
 
.cancelbutton {
  width: auto;
  padding: 10px 18px;
  background-color: #f44336;
}
 
.container {
  padding: 16px;
}
 
span.psw {
  float: right;
  padding-top: 16px;
}
 
/* Change styles for span and cancel button
   on extra small screens */
@media screen and (max-width: 300px) {
  span.psw {
     display: block;
     float: none;
  }
  .cancelbutton {
     width: 100%;
  }
}
</style>
   
<!-- End of css related code which we can have either in 
     same jsp or separately also in a css file -->
 
<!-- Client side validations that need to be handled in javascript,
      it can be handled in separate file or in same jsp -->
<script type="text/javascript">
function ValidateEmail(emailId)
{
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(emailId.value.match(mailformat))
    {
        document.getElementById('password').focus();
        return true;
    }
    else
    {
        alert("You have entered an invalid email address!");
        document.getElementById('emailId').focus();
        return false;
    }
}
</script>

<!-- End of client side validations that need to be handled 
     in javascript, it can be handled in separate file or in same jsp -->
</head>
<body>
 
    <!-- We should have a servlet in order to process the form in
          server side and proceed further -->
    <form action="loginServlet" method="post" onSubmit="ValidateEmail(document.getElementById('emailId'))">
         <div class="container">
    <label for="username"><b>Email</b></label>
    <input type="text" placeholder="Please enter your email" name="emailId" id = "emailId" required>
 
    <label for="password"><b>Password</b></label>
    <input type="password" placeholder="Please enter Password" name="password" id="password" required>
         
    <button type="submit">Login</button>
    <label>
      <input type="checkbox" checked="checked" name="rememberme"> Remember me
    </label>
  </div>
 
  <div class="container" style="background-color:#f1f1f1">
    <button type="button" class="cancelbutton">Cancel</button>
    <span class="psw">Forgot <a href="<%=request.getContextPath()%>/forgotpassword.jsp">password?</a></span>
  </div>
    </form>
</body>
</html>