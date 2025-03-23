<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="matc.persistence.GenericDao" %>
<%@ page import="matc.entity.Gym" %>

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

        <!-- Link to manage gyms -->
        <a href="gymManagement" style="...">Manage Gyms</a>

        <!-- Link to log climb -->
        <a href="climb" style="...">Log a Climb</a>

        <!-- Dynamically list gyms for viewing logs -->
        <h3>View Logs by Gym:</h3>
        <ul>
            <c:forEach var="gym" items="${applicationScope.gyms}">
                <li>
                    <a href="gymPage?gymId=${gym.id}">
                            ${gym.name}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>

</body>
</html>