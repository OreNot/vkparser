<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 16.06.2018
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Результаты проверки</title>
</head>
<p>
<h3><p align="center">Результаты проверки</p></h3><br>

<%
    out.println(request.getAttribute("data"));
%>
<a href="index.jsp">Назад</a>
</body>
</html>
