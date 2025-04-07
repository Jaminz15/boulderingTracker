<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="head.jsp" %>

<html>

<body>

<jsp:include page="header.jsp" />

<div class="main-content">
    <section>
        <h2>Log a New Climb</h2>

        <form action="climb" method="post">
            <input type="hidden" name="action" value="add">

            <label for="date">Date:</label>
            <input type="date" id="date" name="date" required>

            <label for="gym">Gym:</label>
            <select id="gym" name="gymId" required>
                <c:forEach var="gym" items="${gyms}">
                    <option value="${gym.id}">${gym.name}</option>
                </c:forEach>
            </select>

            <label for="climbType">Climb Type:</label>
            <input type="text" id="climbType" name="climbType" required>

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

            <label for="notes">Notes:</label>
            <textarea id="notes" name="notes"></textarea>

            <button type="submit">Save Climb</button>
            <button type="button" onclick="window.location.href='index.jsp'">Cancel</button>
        </form>
    </section>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>