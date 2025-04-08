<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="head.jsp" %>

<html>
<body>

<jsp:include page="header.jsp" />

<div class="main-content">
    <section>
        <h2>Your Climbing Stats</h2>

        <p><strong>Total Climbs:</strong> ${totalClimbs}</p>
        <p><strong>Total Attempts:</strong> ${totalAttempts}</p>
        <p><strong>Average Attempts per Climb:</strong> ${averageAttempts}</p>
        <p><strong>Success Rate:</strong> ${successRate}%</p>
        <p><strong>Best Grade Climbed:</strong> ${bestGrade}</p>

        <h3>Detailed Climbing History</h3>
        <table id="progressTable" class="display">
            <thead>
            <tr>
                <th>Date</th>
                <th>Gym</th>
                <th>Type</th>
                <th>Grade</th>
                <th>Attempts</th>
                <th>Success</th>
                <th>Notes</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty climbs}">
                    <tr>
                        <td colspan="7">No climbing logs available. <a href="climb">Log one now!</a></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="climb" items="${climbs}">
                        <tr>
                            <td>${climb.date}</td>
                            <td>${climb.gym.name}</td>
                            <td>${climb.climbType}</td>
                            <td>${climb.grade}</td>
                            <td>${climb.attempts}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${climb.success}">Yes</c:when>
                                    <c:otherwise>No</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty climb.notes}">
                                        ${climb.notes}
                                    </c:when>
                                    <c:otherwise>No notes</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </section>
</div>

<jsp:include page="footer.jsp" />

<script>
    $(document).ready(function() {
        $('#progressTable').DataTable({
            responsive: true,
            autoWidth: false,
            order: [[0, 'desc']]
        });
    });
</script>

</body>
</html>