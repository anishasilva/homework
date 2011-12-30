<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.*" %>
<%@ page import="edu.depaul.se.account.jpa.*" %>
<%@ page import="edu.depaul.se.account.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show All Account</title>
    </head>
    <body>
        <h1>All Accounts:</h1>
        <UL>
        <%
        AccountManager am = new AccountManager();
        List<IAccount> accounts = am.getAllAccounts();
        for (int i = 0; i < accounts.size(); i++){%>
        	<LI>
        	Account Number:<%=accounts.get(i).getId()%><br>
        	Name 		  :<%=accounts.get(i).getName()%><br>
        	Balance 	  :<%=accounts.get(i).getBalance()%><br>
        	</LI>
        <%}
        %></UL>
    </body>
</html>
