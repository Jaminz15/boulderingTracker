<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="head.jsp" %>

<!-- Page-specific CSS -->
<link rel="stylesheet" href="css/userProfile.css">

<!DOCTYPE html>
<html>
<body>

<jsp:include page="header.jsp" />

<div class="main-content">
    <section class="profile-section">
        <div class="profile-card">
            <img src="images/climb-icon.jpg" alt="Climb Icon" class="profile-icon">

            <h2>User Profile</h2>

            <div class="profile-details">
                <p><strong>Username:</strong> ${user.username}</p>
                <p><strong>Email:</strong> ${user.email}</p>
                <p><strong>Account Created:</strong> ${formattedDate}</p>
                <p><strong>Total Climbs Logged:</strong> ${totalClimbs}</p>

                <c:if test="${not empty favoriteGym}">
                    <p><strong>Favorite Gym:</strong>
                        <span class="gym-badge">${favoriteGym.name}</span>
                    </p>
                </c:if>
            </div>
        </div>
    </section>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>