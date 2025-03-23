<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

        <!-- show last log date -->
        <c:if test="${not empty lastLogDate}">
            <p>Last Log Date: <c:out value="${lastLogDate}" /></p>
        </c:if>

        <!-- Link to manage gyms -->
        <a href="gymManagement" style="margin-right: 10px;">Manage Gyms</a>

        <!-- Link to log climb -->
        <a href="climb">Log a Climb</a>

        <!-- Dynamically list gyms with climbs -->
        <h3>View Logs by Gym:</h3>
        <ul>
            <c:forEach var="gym" items="${gyms}">
                <li>
                    <a href="gymPage?gymId=${gym.id}">
                            ${gym.name}
                    </a>
                </li>
            </c:forEach>
        </ul>
        <!-- If the user has no gyms (no logged climbs) -->
        <c:if test="${empty gyms}">
            <p>You haven’t logged any climbs yet. <a href="climb">Log one now!</a></p>
        </c:if>
    </c:otherwise>
</c:choose>

</body>
</html>