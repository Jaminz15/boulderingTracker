<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Climbing Logs</title>
</head>
<body>

<h2>Climbing Logs</h2>

<table border="1">
  <tr>
    <th>Date</th>
    <th>Gym</th>
    <th>Climb Type</th>
    <th>Grade</th>
    <th>Attempts</th>
    <th>Success</th>
    <th>Notes</th>
  </tr>

  <c:choose>
    <c:when test="${empty climbs}">
      <tr>
        <td colspan="7">No climb data available.</td>
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
</table>

<!-- Back to Home -->
<a href="index.jsp" style="font-size: 18px; text-decoration: none; padding: 10px; background-color: #28a745; color: white; border-radius: 5px;">
  Back to Home
</a>

</body>
</html>