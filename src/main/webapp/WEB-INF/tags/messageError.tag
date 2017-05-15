<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="listError" required="true" type="java.util.ArrayList" %>
<ul>
    <c:forEach var="error" items="${listError}">
        <li><spring:message code="${error}" /></a></li>
    </c:forEach>
</ul>