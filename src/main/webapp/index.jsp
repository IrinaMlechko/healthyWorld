<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your service</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }

        form {
            width: 300px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 20px;
            border: 1px solid #cccccc;
            border-radius: 5px;
        }

        input[type="text"],
        input[type="password"] {
            width: 90%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #cccccc;
            border-radius: 3px;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        p {
            text-align: center;
        }

        a {
            color: #4CAF50;
            text-decoration: none;
        }
    </style>
</head>
<body>
<br/>
<form action="controller" method="post">
    <input type="hidden" name="command" value="login"/>
    <label for="login">Login:</label>
    <br/>
    <input type="text" name="login" id="login" value=""/>
    <br/>
    <label for="password">Password:</label>
    <br/>
    <input type="password" name="password" id="password" value=""/>
    <br/>
    <input type="submit" name="submit" value="Submit"/>
    <br/>
    ${failed}
</form>
<br/>
<form action="pages/registration.jsp">
    <p>Not a user yet? <a href="pages/registration.jsp">Sign up</a></p>
</form>
</body>
</html>
