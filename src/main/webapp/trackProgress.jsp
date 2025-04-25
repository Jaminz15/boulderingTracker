<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="head.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Track Progress</title>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="main-content">
    <section class="stats-section">
        <h2>Your Climbing Stats</h2>

        <form method="get" action="trackProgress" class="filter-form">
            <label for="gymId">Filter by Gym:</label>
            <select name="gymId" id="gymId">
                <option value="">All Gyms</option>
                <c:forEach var="gym" items="${gyms}">
                    <option value="${gym.id}" ${param.gymId == gym.id ? "selected" : ""}>${gym.name}</option>
                </c:forEach>
            </select>

            <label for="startDate">Start Date:</label>
            <input type="date" name="startDate" id="startDate" value="${param.startDate}" />

            <label for="endDate">End Date:</label>
            <input type="date" name="endDate" id="endDate" value="${param.endDate}" />

            <button type="submit">Apply Filters</button>
        </form>

        <div class="stats-summary">
            <p><strong>Total Climbs:</strong> ${totalClimbs}</p>
            <p><strong>Total Attempts:</strong> ${totalAttempts}</p>
            <p><strong>Average Attempts per Climb:</strong> ${averageAttempts}</p>
            <p><strong>Success Rate:</strong> ${successRate}%</p>
            <p><strong>Best Grade Climbed:</strong> ${bestGrade}</p>

            <c:if test="${not empty hardestClimb}">
                <p><strong>Hardest Climb Logged:</strong> ${hardestClimb.grade} at ${hardestClimb.gym.name}</p>
            </c:if>

            <c:if test="${not empty mostAttempts}">
                <p><strong>Most Attempts:</strong> ${mostAttempts.attempts} on ${mostAttempts.grade} at ${mostAttempts.gym.name}</p>
            </c:if>
        </div>

        <hr />
        <h3>Filtered Climb Logs</h3>

        <c:if test="${not empty userClimbs}">
            <table class="track-table">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Gym</th>
                    <th>Grade</th>
                    <th>Attempts</th>
                    <th>Success</th>
                    <th>Notes</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="climb" items="${userClimbs}">
                    <tr>
                        <td>${climb.date}</td>
                        <td>${climb.gym.name}</td>
                        <td>${climb.grade}</td>
                        <td>${climb.attempts}</td>
                        <td>
                            <c:choose>
                                <c:when test="${climb.success}">
                                    <span style="color: green;">Success</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: red;">Fail</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${climb.notes}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty userClimbs}">
            <p>No climbs found for the selected filters.</p>
        </c:if>
    </section>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>