<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>

<!-- Page-specific CSS -->
<link rel="stylesheet" href="css/editClimb.css">

<html>
<body>
<jsp:include page="header.jsp" />
<div class="main-content">
  <section class="edit-climb-section">
    <h2>Edit Climb</h2>

    <form action="climb" method="post">
      <input type="hidden" name="action" value="update">
      <input type="hidden" name="climbId" value="${climb.id}">

      <!-- Error Message Display -->
      <c:if test="${not empty param.error}">
        <div class="error-message">
          <c:choose>
            <c:when test="${param.error == 'missingClimbType'}">
              <p>Please enter a climb type.</p>
            </c:when>
            <c:when test="${param.error == 'invalidAttempts'}">
              <p>Attempts must be a positive number.</p>
            </c:when>
            <c:when test="${param.error == 'missingGrade'}">
              <p>Please select a grade.</p>
            </c:when>
            <c:when test="${param.error == 'invalidClimbId'}">
              <p>Climb ID is invalid or missing.</p>
            </c:when>
            <c:otherwise>
              <p>Something went wrong. Please try again.</p>
            </c:otherwise>
          </c:choose>
        </div>
      </c:if>

      <label for="date">Date:</label>
      <input type="date" id="date" name="date" value="${climb.date}" required>

      <label for="gym">Gym:</label>
      <select id="gym" name="gymId" required>
        <c:forEach var="gym" items="${gyms}">
          <option value="${gym.id}" ${gym.id == climb.gym.id ? 'selected' : ''}>
              ${gym.name}
          </option>
        </c:forEach>
      </select>

      <label for="climbType">Climb Type:</label>
      <input type="text" id="climbType" name="climbType" value="${climb.climbType}" required>

      <label for="grade">Grade:</label>
      <select id="grade" name="grade" required>
        <c:forEach var="v" items="${['V1','V2','V3','V4','V5','V6','V7','V7+']}">
          <option value="${v}" ${v == climb.grade ? 'selected' : ''}>${v}</option>
        </c:forEach>
      </select>

      <label for="attempts">Attempts:</label>
      <input type="number" id="attempts" name="attempts" value="${climb.attempts}" min="1" required>

      <label for="success">Completed?</label>
      <select id="success" name="success">
        <option value="true" ${climb.success ? 'selected' : ''}>Yes</option>
        <option value="false" ${!climb.success ? 'selected' : ''}>No</option>
      </select>

      <label for="notes">Notes:</label>
      <textarea id="notes" name="notes">${climb.notes}</textarea>

      <button type="submit">Update Climb</button>
      <button type="button" onclick="window.location.href='dashboard'">Cancel</button>
    </form>
  </section>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>