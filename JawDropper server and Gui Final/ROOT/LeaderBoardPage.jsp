<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
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

table, th{
   border: 1px solid #1C6EA4;
  background-color: #FF6F33;
  width: 20%;
  text-align: center;
  border-collapse: collapse;
}
</style>
</head>
<body>
	<meta charset="utf-8"><title>LeaderBoard</title>
</head>

<body  style="background-color:#000000;" link="#000" alink="#017bf5" vlink="#000" >
	
	
	<center><font face="Lucida Bright" color="#f0f0f0" size="8" ><b>JawDropper</b></font>
	<br />
	<form method ="post" action="/">
	<input type="submit" value="Home">
	</form>

<body>
<br /><br /><br />

<center><font face="Helvetica" color="#f0f0f0" size="4">
Leader Board 
</font>
<br /><br />
<table>
<tr>
   <th>rank</th>
   <th>machine</th>
   <th>Total candy dispensed</th>
 </tr>
	<c:forEach var = "i" begin = "0" end = "${machines.size()-1}">
		<tr>
			<td>${i+1}</td>
			<td>${machines.get(i).name()}</td>
			<td>${machines.get(i).totalCandyDispensed()}</td>
		</tr>
		</c:forEach>
</table>

</body>
</html>