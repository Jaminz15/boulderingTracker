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
    <section>
        <h2>Your Climbing Stats</h2>

        <p><strong>Total Climbs:</strong> ${totalClimbs}</p>
        <p><strong>Total Attempts:</strong> ${totalAttempts}</p>
        <p><strong>Average Attempts per Climb:</strong> ${averageAttempts}</p>
        <p><strong>Success Rate:</strong> ${successRate}%</p>
        <p><strong>Best Grade Climbed:</strong> ${bestGrade}</p>

    </section>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>