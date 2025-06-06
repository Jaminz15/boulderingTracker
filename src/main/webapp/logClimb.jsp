<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="head.jsp" />

<!-- Page-specific CSS -->
<link rel="stylesheet" href="css/logClimb.css">

<!DOCTYPE html>
<html>
<body>

<jsp:include page="header.jsp" />

<div class="main-content">
    <section class="log-section">

        <!-- Card-styled form -->
        <div class="log-card">
            <h2>Log a New Climb</h2>

            <form action="climb" method="post">
                <input type="hidden" name="action" value="add">

                <!-- Error Message Display -->
                <c:if test="${not empty param.error}">
                    <div class="error-message">
                        <c:choose>
                            <c:when test="${param.error == 'missingClimbType'}">
                                <p>Please enter a climb type.</p>
                            </c:when>
                            <c:when test="${param.error == 'missingGrade'}">
                                <p>Please select a grade.</p>
                            </c:when>
                            <c:when test="${param.error == 'invalidAttempts'}">
                                <p>Attempts must be a positive number.</p>
                            </c:when>
                            <c:when test="${param.error == 'unexpected'}">
                                <p>Something went wrong. Please try again.</p>
                            </c:when>
                            <c:otherwise>
                                <p>Unknown error occurred.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>

                <label for="date">Date:</label>
                <input type="date" id="date" name="date" required>

                <label for="gym">Gym:</label>
                <select id="gym" name="gymId" required>
                    <c:forEach var="gym" items="${gyms}">
                        <option value="${gym.id}">${gym.name}</option>
                    </c:forEach>
                </select>

                <c:set var="selectedType" value="${climb != null ? climb.climbType : ''}" />
                <label for="climbType">Climb Type:</label>
                <select id="climbType" name="climbType" required>
                    <option value="Slab" ${climb.climbType == 'Slab' ? 'selected' : ''}>Slab</option>
                    <option value="Overhang" ${climb.climbType == 'Overhang' ? 'selected' : ''}>Overhang</option>
                    <option value="Dyno" ${climb.climbType == 'Dyno' ? 'selected' : ''}>Dyno</option>
                    <option value="Vertical" ${climb.climbType == 'Vertical' ? 'selected' : ''}>Vertical</option>
                    <option value="Roof" ${climb.climbType == 'Roof' ? 'selected' : ''}>Roof</option>
                </select>

                <label for="grade">Grade:</label>
                <select id="grade" name="grade" required>
                    <option value="V1">V1</option>
                    <option value="V2">V2</option>
                    <option value="V3">V3</option>
                    <option value="V4">V4</option>
                    <option value="V5">V5</option>
                    <option value="V6">V6</option>
                    <option value="V7">V7</option>
                    <option value="V7+">V7+</option>
                </select>

                <label for="attempts">Attempts:</label>
                <input type="number" id="attempts" name="attempts" min="1" required>

                <label for="success">Completed?</label>
                <select id="success" name="success">
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>

                <div class="form-group">
                    <label for="notes">Notes:</label>
                    <textarea id="notes" name="notes"></textarea>
                </div>

                <button type="submit">Save Climb</button>
                <button type="button" onclick="window.location.href='index.jsp'">Cancel</button>
            </form>
        </div>

        <!-- Table section outside card -->
        <div class="logs-header">
            <h2>Your Climbing Logs</h2>
        </div>
        <div class="table-container">
            <table id="climbsTable" class="display">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Climb Type</th>
                    <th>Grade</th>
                    <th>Attempts</th>
                    <th>Success</th>
                    <th>Notes</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
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
                        <td>${climb.notes}</td>
                        <td>
                            <a href="editClimb?climbId=${climb.id}" class="btn-edit">Edit</a>
                            <form action="climb"
                                  method="post"
                                  style="display:inline;"
                                  onsubmit="return confirm('Are you sure you want to delete this climb?');">
                                <input type="hidden" name="climbId" value="${climb.id}">
                                <button type="submit" name="action" value="delete" class="btn-delete">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </section>
</div>

<jsp:include page="footer.jsp" />

<!-- DataTable Initialization -->
<script>
    $(document).ready(function() {
        $('#climbsTable').DataTable({
            responsive: true,
            autoWidth: false
        });
    });
</script>

</body>
</html>