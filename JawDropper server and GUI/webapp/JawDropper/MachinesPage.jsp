<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
${machines.get(i).channelId()}
<form method ="post" action="LogTablePageHandler">
<select name="machines" id="machines">
  <c:forEach var = "i" begin = "0" end = "${machines.size()-1}">
         <option value="${machines.get(i).channelId()}">${machines.get(i).nameWithId()}</option>
      </c:forEach>
<center><input type="submit"  value="submit">
</select>	  
	 
</body>
</html>