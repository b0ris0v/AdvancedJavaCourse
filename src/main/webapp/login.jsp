<!DOCTYPE html>
<html>
    <head>
        <meta charset="US-ASCII">
        <title>Login Page</title>
    </head>
    <body>
        <%= session.getAttribute("mySecretMessage") %>
        <c:if session.getAttribute("cashedName")="null">
        </c:if>
        <form action="LoginServlet" method="post">
            Username: <input type="text" name="user">
            <br>
            <!--Password: <input type="password" name="pwd">-->
            <!--<br>-->
            <input type="submit" value="Login">
        </form>
        <c:if session.getAttribute("cashedName")!="null">
            Waiting authorisation..
        </c:if>
    </body>
</html>