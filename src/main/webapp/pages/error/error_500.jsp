<%--
  Created by IntelliJ IDEA.
  User: irynamlechka
  Date: 29.06.23
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Internal Server Error!</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }

        h1 {
            text-align: center;
        }

        p {
            text-align: center;
            margin-top: 20px;
            font-size: 16px;
        }
    </style>
</head>
<body>
<h1>Internal Server Error!</h1>
<p>Request from: <strong>${pageContext.errorData.requestURI}</strong> has failed.</p>
<p>Servlet name: <strong>${pageContext.errorData.servletName}</strong></p>
<p>Status code: <strong>${pageContext.errorData.statusCode}</strong></p>
<p>Exception: <strong>${pageContext.exception}</strong></p>
<br/><br/><br/>
<p>Message from exception: <strong>${error_msg}</strong></p>
</body>
</html>
