<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<style> 
input[value=Home]{
  background-color: #FF6F33;
  border: none;
  color: white;
  padding: 16px 32px;
  text-decoration: none;
  margin: 4px 2px;
  cursor: pointer;
  width: 20%
}
</style>
	<meta charset="utf-8"><title>Machines</title>
</head>

<body  style="background-color:#000000;" link="#000" alink="#017bf5" vlink="#000" >
	
	
	<center><font face="Lucida Bright" color="#f0f0f0" size="8" ><b>JawDropper</b></font>
	<br />
	<form method ="post" action="/">
	<input type="submit" value="Home" name="Home">
	</form>

<br /><br /><br /><br /><br /><br /><br />

<form method ="post" action="LogTablePageHandler">
<font face="Helvetica" color="#f0f0f0" size="4">
Chose a machine : 
<select name="machines" id="machines">
  <c:forEach var = "i" begin = "0" end = "${machines.size()-1}">
         <option value="${machines.get(i).channelId()}">${machines.get(i).nameWithId()}</option>
      </c:forEach>
<input type="submit"  value="submit">
</form>
</font>

	
</body>
</html>