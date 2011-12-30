<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invalid Data Entry</title>
    </head>
    <body>
        <h1>Check your inputs!</h1>
        <h3>Bad account input: <%=request.getParameter("account") %></h3>
        <h3>Bad amount input: <%=request.getParameter("amount") %></h3>
    </body>
</html>
