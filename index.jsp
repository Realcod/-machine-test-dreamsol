<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration form</title>
<style>
   .edit{
   margin: 15px;
   }
</style>
</head>
<body>
<form action="register" method="post">
  <label for="name">Name:</label>
  <input type="text"  name="name"  class="edit" required><br>
  <label for="mobileno">Mobile No:</label>
  <input type="text" name="mobileno" class="edit" required> <br>
  <label for="email">Email Id:</label>
  <input type="email" name="email" class="edit" required> <br>
  <label for="dob">Date of Birth:</label>
  <input type="date" name="dob" class="edit" required> <br>
  <button type="submit" class="edit">Register</button>
  <button><a href="showdata">View Users</a></button>
</form> 
</body>
</html>
