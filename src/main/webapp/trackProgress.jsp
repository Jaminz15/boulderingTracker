<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Track Progress</title>
</head>
<body>

<header>
    <h1>BoulderBook - Track Progress</h1>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="climb">Log a Climb</a>
        <a href="gymManagement">Manage Gyms</a>
        <a href="profile">Profile</a>
    </nav>
</header>

<section>
    <h2>Your Climbing Stats</h2>

    <p><strong>Total Climbs:</strong> ${totalClimbs}</p>
    <p><strong>Total Attempts:</strong> ${totalAttempts}</p>
    <p><strong>Average Attempts per Climb:</strong> ${averageAttempts}</p>
    <p><strong>Success Rate:</strong> ${successRate}%</p>
    <p><strong>Best Grade Climbed:</strong> ${bestGrade}</p>

</section>

<footer>
    <p>&copy; 2025 BoulderBook</p>
</footer>

</body>
</html>