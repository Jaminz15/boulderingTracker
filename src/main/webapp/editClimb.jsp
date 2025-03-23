<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Edit Climb</title>
</head>
<body>
<header>
  <h1>BoulderBook - Edit Climb</h1>
  <nav>
    <a href="dashboard">Dashboard</a>
    <a href="trackProgress.jsp">Track Progress</a>
    <a href="gymManagement">Manage Gyms</a>
  </nav>
</header>

<section>
  <h2>Edit Climb</h2>

  <form action="climb" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="climbId" value="${climb.id}">

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

