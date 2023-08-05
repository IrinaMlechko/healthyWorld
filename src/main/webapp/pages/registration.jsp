<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your service - Registration</title>
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

        span.small-text {
            font-size: 12px;
            color: #888;
        }
    </style>
</head>
<body>
<br/>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="signup"/>
    <label for="login">Login:</label>
    <br/>
    <input type="text" name="login" id="login" value=""/>
    <br/>
    <label for="password">Password:</label>
    <br/>
    <input type="password" name="password" id="password" value=""/>
    <br/>
    <label for="firstName">First Name:</label>
    <br/>
    <input type="text" name="firstName" id="firstName" value=""/>
    <br/>
    <label for="lastName">Last Name:</label>
    <br/>
    <input type="text" name="lastName" id="lastName" value=""/>
    <br/>
    <label for="dateOfBirth">Date of Birth:</label>
    <br/>
    <input type="text" name="dateOfBirth" id="dateOfBirth" value=""/>
    <br/>
    <span class="small-text">(Format: dd-MM-yyyy)</span>
    <br/>
    <input type="submit" name="submit" value="Register"/>
    <br/>
    ${failed}
</form>
</body>
</html>
