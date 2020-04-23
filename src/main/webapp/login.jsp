<!DOCTYPE html>
<html>
    <head>
        <meta charset="US-ASCII">
        <title>Login Page</title>
    </head>
    <body>
        Name: <%= session.getAttribute("name") %>
        id: <%= session.getAttribute("id") %>
    </body>
</html>