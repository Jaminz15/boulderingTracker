<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="head.jsp" %>

<html>
<body>

<jsp:include page="header.jsp" />

<div class="main-content">
    <section class="home-section">
        <div class="hero-message">
            <h1>Welcome to BoulderBook</h1>
            <p>Track your bouldering progress!</p>
        </div>

        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <div class="center-login-btn">
                    <a href="logIn" class="login-btn">Log In</a>
                </div>
            </c:when>
            <c:otherwise>
                <h3>Welcome, ${sessionScope.user.username}!</h3>

                <c:if test="${not empty lastLogDate}">
                    <p>Last Log Date: <c:out value="${lastLogDate}" /></p>
                </c:if>

                <h3>Your Gyms</h3>
                <table id="gymsDashboardTable" class="display">
                    <thead>
                    <tr>
                        <th>Gym Name</th>
                        <th>View Logs</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty gyms}">
                            <tr>
                                <td colspan="2">You haven’t logged any climbs yet. <a href="climb">Log one now!</a></td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="gym" items="${gyms}">
                                <tr>
                                    <td>${gym.name}</td>
                                    <td><a href="gymPage?gymId=${gym.id}">View Logs</a></td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </section>
</div>

<jsp:include page="footer.jsp" />

<script>
    $(document).ready(function() {
        $('#gymsDashboardTable').DataTable({
            responsive: true,
            autoWidth: false
        });
    });
</script>

</body>
</html>