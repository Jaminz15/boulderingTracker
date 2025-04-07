<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="head.jsp" %>

<!DOCTYPE html>
<html>
<body>

<jsp:include page="header.jsp" />

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

<jsp:include page="footer.jsp" />

</body>
</html>