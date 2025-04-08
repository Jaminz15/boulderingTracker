<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="site-header">

    <!-- NAV BAR AT TOP -->
    <nav class="main-nav">
        <div class="container">
            <ul>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="dashboard">Dashboard</a></li>
                    <li><a href="trackProgress">Track Progress</a></li>
                    <li><a href="climb">Log a Climb</a></li>
                    <li><a href="gymManagement">Manage Gyms</a></li>
                    <li><a href="profile">Profile</a></li>
                </c:if>
            </ul>
        </div>
    </nav>

    <!-- LOGO + ACCOUNT INFO -->
    <div class="container">
        <div class="top-bar">
            <div class="logo-section">
                <h1>BoulderBook</h1>
                <p class="tagline">Climb Smarter</p>
            </div>
            <div class="account-links">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <span>Welcome, ${sessionScope.user.username}</span> |
                        <a href="logout">Log Out</a>
                    </c:when>
                    <c:otherwise>
                        <a href="logIn">Log In</a> / <a href="signUp">Sign Up</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</header>