<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Not Found</title>
    </head>
    <body>
        <h1>Sorry the account you entered was not found.  Try again.</h1>
        
        <br>Account:<%=request.getParameter("account") %>
    </body>
</html>
