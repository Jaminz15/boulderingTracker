<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="head.jsp" %>

<html>
<body>

<jsp:include page="header.jsp" />

<div class="main-content">
    <section class="home-section">
        <h1>Welcome to BoulderBook</h1>
        <p>Track your bouldering progress!</p>

        <c:choose>
            <c:when test="${empty userName}">
                <a href="logIn" class="btn btn-success" style="font-size: 18px; text-decoration: none; padding: 10px;">
                    Log In
                </a>
            </c:when>
            <c:otherwise>
                <h3>Welcome, ${userName}!</h3>

                <c:if test="${not empty lastLogDate}">
                    <p>Last Log Date: <c:out value="${lastLogDate}" /></p>
                </c:if>

                <h3>View Logs by Gym:</h3>
                <ul>
                    <c:forEach var="gym" items="${gyms}">
                        <li>
                            <a href="gymPage?gymId=${gym.id}">${gym.name}</a>
                        </li>
                    </c:forEach>
                </ul>

                <c:if test="${empty gyms}">
                    <p>You haven’t logged any climbs yet. <a href="climb">Log one now!</a></p>
                </c:if>
            </c:otherwise>
        </c:choose>
    </section>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>