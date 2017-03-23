<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="debut" required="true" %>
<%@ attribute name="fin" required="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="pagination">
    <c:if test="${debut > 0}">
        <li>
            <a href="/dashboard?currentPage=${currentPage-1}&sizePages=${sizePages}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
    </c:if>
    <c:forEach begin="${debut}" end="${fin}" varStatus="loop">
        <li><a href="/dashboard?currentPage=${loop.index}&sizePages=${sizePages}">${loop.index}</a></li>
    </c:forEach>
    <c:if test="${fin > currentPage + 1}">
        <li>
            <a href="/dashboard?currentPage=${currentPage+1}&sizePages=${sizePages}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </c:if>
</ul>