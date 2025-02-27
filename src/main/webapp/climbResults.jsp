<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="matc.entity.Climb" %>
<%@ page import="matc.entity.Gym" %>
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

  <%
    List<Climb> climbs = (List<Climb>) request.getAttribute("climbs");

    if (climbs == null || climbs.isEmpty()) {
  %>
  <tr>
    <td colspan="7">No climb data available.</td>
  </tr>
  <%
  } else {
    for (Climb climb : climbs) {
  %>
  <tr>
    <td><%= climb.getDate() %></td>
    <td><%= climb.getGym().getName() %></td>
    <td><%= climb.getClimbType() %></td>
    <td><%= climb.getGrade() %></td>
    <td><%= climb.getAttempts() %></td>
    <td><%= climb.isSuccess() ? "Yes" : "No" %></td>
    <td><%= climb.getNotes() != null ? climb.getNotes() : "No notes" %></td>
  </tr>
  <%
      }
    }
  %>
</table>

<!-- Back to Home -->
<a href="index.jsp" style="font-size: 18px; text-decoration: none; padding: 10px; background-color: #28a745; color: white; border-radius: 5px;">
  Back to Home
</a>

</body>
</html>