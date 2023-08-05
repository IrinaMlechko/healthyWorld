<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Main Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }

        .header {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 20px;
        }

        .header p {
            font-weight: bold;
            margin-right: 20px;
        }

        .content {
            text-align: center;
        }

        .content h1 {
            margin-bottom: 20px;
        }

        .content table {
            margin: 0 auto;
            border-collapse: collapse;
            width: 80%;
        }

        .content table th,
        .content table td {
            padding: 8px;
            border: 1px solid #cccccc;
        }

        .no-medicines-message {
            font-style: italic;
            color: #999999;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="header">
    <p>Welcome, ${sessionScope.userName}!</p>
    <form action="controller">
        <input type="hidden" name="command" value="logout"/>
        <input type="submit" value="Log Out"/>
    </form>
</div>
<div class="content">
    <h1>Medicines</h1>
    <c:if test="${not empty medicines}">
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Manufacturer</th>
                <th>Price</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${medicines}" var="medicine">
                <tr>
                    <td>${medicine.name}</td>
                    <td>${medicine.manufacturer}</td>
                    <td>${medicine.price}</td>
                    <td>${medicine.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
<%--    <c:if test="${empty medicines}">--%>
<%--        <p class="no-medicines-message">No medicines found.</p>--%>
<%--    </c:if>--%>
</div>
</body>
</html>
