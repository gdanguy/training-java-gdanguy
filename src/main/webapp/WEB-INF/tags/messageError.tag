<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="listError" required="true" type="java.util.ArrayList" %>
<ul>
    <c:forEach var="error" items="${listError}">
        <li>${error}</li>
    </c:forEach>
</ul>