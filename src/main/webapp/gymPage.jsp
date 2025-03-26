<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Gym Page</title>
</head>
<body>

<header>
  <h1>BoulderBook - Gym Page</h1>
  <nav>
    <a href="index.jsp">Dashboard</a>
    <a href="trackProgress">Track Progress</a>
    <a href="logClimb.jsp">Log a Climb</a>
  </nav>
</header>

<section>
  <h2>Gym Page: <c:out value="${gym.name}"/></h2>

  <label for="filterDate">Filter by Date:</label>
  <input type="date" id="filterDate" name="filterDate">
  <!-- Filter logic will be implemented later -->

  <h3>Climbing Logs</h3>
  <table border="1">
    <tr>
      <th>Date</th>
      <th>Type</th>
      <th>Grade</th>
      <th>Attempts</th>
      <th>Success</th>
      <th>Notes</th>
      <th>Actions</th>
    </tr>

    <c:choose>
      <c:when test="${empty climbs}">
        <tr>
          <td colspan="7">No climbing logs for this gym.</td>
        </tr>
      </c:when>
      <c:otherwise>
        <c:forEach var="climb" items="${climbs}">
          <tr>
            <td>${climb.date}</td>
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
            <td>
              <!-- Placeholder for Edit/Delete -->
              <form action="climb" method="post" style="display:inline;">
                <input type="hidden" name="climbId" value="${climb.id}">
                <button type="submit" name="action" value="delete">Delete</button>
              </form>
              <a href="editClimb?climbId=${climb.id}">Edit</a>
            </td>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </table>

  <br>
  <a href="dashboard">Back to Dashboard</a>
</section>

<footer>
  <p>&copy; 2025 BoulderBook</p>
</footer>

</body>
</html>