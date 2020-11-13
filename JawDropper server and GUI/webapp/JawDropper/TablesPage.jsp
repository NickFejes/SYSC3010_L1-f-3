<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<style>
table, th, td {
  border: 1px solid black;
}

table {
  width: 100%;
}
</style>
</head>
<body>


<center><h2>Total candy dispensed : ${record.totalCandy()}</h2>
<center><h2>Candy currentlt in dispensed : ${record.currentCandy()}</h2>

<table>
 <tr>
   <th>Date Time</th>
 </tr>
	<c:forEach var = "i" begin = "0" end = "${record.size()-1}">
		<tr>
			<td>${record.getDateTime(i)}</td>
		</tr>
		</c:forEach>
</table>

</body>
</html>