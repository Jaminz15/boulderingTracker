<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Gym Management</title>
</head>
<body>
<header>
    <h1>BoulderBook - Gym Management</h1>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="trackProgress">Track Progress</a>
        <a href="climb">Log a Climb</a>
    </nav>
</header>

<section>
    <h2>Manage Your Gyms</h2>

    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Location</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="gym" items="${gyms}">
            <tr>
                <td>${gym.name}</td>
                <td>${gym.location}</td>
                <td>
                    <form action="gymManagement" method="post">
                        <input type="hidden" name="gymId" value="${gym.id}">
                        <button type="submit" name="action" value="delete">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>Add a New Gym</h3>
    <form action="gymManagement" method="post">
        <label for="gymName">Gym Name:</label>
        <input type="text" id="gymName" name="gymName" required>

        <label for="gymLocation">Location:</label>
        <input type="text" id="gymLocation" name="gymLocation" required>
        <small style="display:block; color: #555; margin-bottom:10px;">
            Try using full street addresses or well-known areas for best results.
        </small>

        <button type="submit" name="action" value="add">Add Gym</button>
    </form>
</section>

<footer>
    <p>&copy; 2025 BoulderBook</p>
</footer>
</body>
</html>