<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to BoulderBook</title>
</head>
<body>

<h1>Welcome to BoulderBook</h1>
<p>Track your bouldering progress!</p>

<!-- Check if user is logged in -->
<c:choose>
    <c:when test="${empty userName}">
        <a href="logIn" style="font-size: 18px; text-decoration: none; padding: 10px; background-color: #28a745; color: white; border-radius: 5px;">
            Log In
        </a>
    </c:when>
    <c:otherwise>
        <h3>Welcome, ${userName}!</h3>

        <a href="searchClimbs" style="font-size: 18px; text-decoration: none; padding: 10px; background-color: #007bff; color: white; border-radius: 5px;">
            View Climbing Logs
        </a>

        <a href="gymManagement" style="font-size: 18px; text-decoration: none; padding: 10px; background-color: #ffc107; color: black; border-radius: 5px;">
            Manage Gyms
        </a>

        <!-- Add Log a Climb Button -->
        <a href="logClimb.jsp" style="font-size: 18px; text-decoration: none; padding: 10px; background-color: #28a745; color: white; border-radius: 5px;">
            Log a Climb
        </a>
    </c:otherwise>
</c:choose>

</body>
</html>
