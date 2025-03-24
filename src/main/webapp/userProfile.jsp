<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
</head>
<body>

<header>
    <h1>BoulderBook - User Profile</h1>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="climb">Log a Climb</a>
        <a href="gymManagement">Manage Gyms</a>
        <a href="trackProgress.jsp">Track Progress</a>
    </nav>
</header>

<section>
    <h2>User Profile</h2>
    <p><strong>Username:</strong> ${user.username}</p>
    <p><strong>Email:</strong> ${user.email}</p>
    <p><strong>Account Created:</strong> ${formattedDate}</p>
    <p><strong>Total Climbs Logged:</strong> ${totalClimbs}</p>

    <c:if test="${not empty favoriteGym}">
        <p><strong>Favorite Gym:</strong> ${favoriteGym.name}</p>
    </c:if>
</section>

<footer>
    <p>&copy; 2025 BoulderBook</p>
</footer>

</body>
</html>