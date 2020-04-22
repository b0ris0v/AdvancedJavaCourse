<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login Success Page</title>
</head>
<body>
<%
String userName = null;
userName = session.getAttribute("sessionName").toString();
Cookie[] cookies = request.getCookies();
if(cookies !=null){
    for(Cookie cookie : cookies){
        if(cookie.getName().equals("user")) userName = cookie.getValue();
    }
}

if(userName == null) response.sendRedirect("login.jsp");
%>
<h3>Hi <%=userName %>, Login successful.</h3>
<br>
<form action="LogoutServlet" method="post">
<input type="submit" value="Logout" >
<%
		try
		{
			HttpSession session1 = request.getSession();
			if (session1.isNew())
			{
				System.out.printf("Success - New Session: %s%n", session1.getId());
			}
			else
			{
				System.out.printf("Success - Old Session: %s%n", session1.getId());
			}
		}
		catch (IllegalStateException ex)
		{
			System.out.println("Success - Exception!" + ex);
		}
%>
</form>
</body>
</html>