<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="head.jsp" %>

<html>
<body>

<jsp:include page="header.jsp" />

<div class="container mt-5">
    <h2>Oops! Something Went Wrong</h2>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <p>We're sorry, but something unexpected happened. Please try again later.</p>
    <a href="index.jsp" class="btn btn-primary">Return to Home</a>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>