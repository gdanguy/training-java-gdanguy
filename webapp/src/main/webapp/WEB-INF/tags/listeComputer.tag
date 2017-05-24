<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="page" %>
<%@ attribute name="listComputers" required="true" type="java.util.ArrayList" %>
<%@ attribute name="path" required="true" %>
<!-- Browse attribute computers -->
<c:forEach var="computer" items="${listComputers}">
    <tr>
        <td class="editMode">
            <input type="checkbox" name="cb${computer.id}" class="cb" value="${computer.id}">
        </td>
        <td>
            <a href=<page:link link="/computer/editComputer" path="${path}" id="${computer.id}"></page:link> onclick="">${computer.name}</a>
        </td>
        <td>${computer.introduced}</td>
        <td>${computer.discontinued}</td>
        <td>${computer.companyName}</td>
    </tr>
</c:forEach>