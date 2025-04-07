<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="includeLeaflet" value="true" />
<%@ include file="head.jsp" %>

<html>

<body>
<jsp:include page="header.jsp" />

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

<!--Add Leaflet Map Preview -->
<section>
    <h3>Map of Your Gyms</h3>
    <div id="gymMap" style="height: 400px; margin-bottom: 2rem;"></div>
</section>

<jsp:include page="footer.jsp" />

<!--Leaflet JS (at end of body) -->
<script
        src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
        crossorigin=""
></script>

<!--JavaScript to load the map and markers -->
<script>
    const map = L.map('gymMap').setView([43.0731, -89.4012], 12); // Default: Madison

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution:
            '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    // Add gym markers
    <c:forEach var="gym" items="${gyms}">
    <c:if test="${not empty gym.latitude && not empty gym.longitude}">
    L.marker([${gym.latitude}, ${gym.longitude}])
        .addTo(map)
        .bindPopup('<strong>${gym.name}</strong><br>${gym.location}');
    </c:if>
    </c:forEach>
</script>
</body>
</html>