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
table, th{
   border: 1px solid #1C6EA4;
  background-color: #FF6F33;
  width: 20%;
  text-align: center;
  border-collapse: collapse;
}
</style>
	<meta charset="utf-8"><title>MachinesTables</title>
</head>

<body  style="background-color:#000000;" link="#000" alink="#017bf5" vlink="#000" >
	
	
	<center><font face="Lucida Bright" color="#f0f0f0" size="8" ><b>JawDropper</b></font>
	<br />
	<form method ="post" action="/">
	<input type="submit" value="Home">
	</form>

<body>
<br /><br /><br />

<center><font face="Helvetica" color="#f0f0f0" size="4">Name of machine : </font><font face="Helvetica" color="#FF6F33">${record.name()}</font>
<center><font face="Helvetica" color="#f0f0f0" size="4">Total candy dispensed : </font><font face="Helvetica" color="#FF6F33">${record.totalCandy()}</font>
<center><font face="Helvetica" color="#f0f0f0" size="4">Candy currently in dispenser : </font><font face="Helvetica"  color="#FF6F33">${record.currentCandy()}</font>
<center><font face="Helvetica" color="#f0f0f0" size="4">Location : </font><font face="Helvetica" color="#FF6F33">${record.location()}</font>
<br/><br />
<form method ="post" action="RemoveMachineHandler">
<center><input type="submit"  value="Remove Machine">
</form



<br/><br /><br />
<table>
 <tr>
   <th>Date Time (year:month:day hh:mm:ss)</th>
 </tr>
	<c:forEach var = "i" begin = "0" end = "${record.size()-1}">
		<tr>
			<td>${record.getDateTime(i)}</td>
		</tr>
		</c:forEach>
</table>

</body>
</html>