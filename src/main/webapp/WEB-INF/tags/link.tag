<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="link" required="true"%>
<%@ attribute name="page"%>
<%@ attribute name="sizePages"%>
<c:set var="first" value="${1}"/>
${link}<c:if test="${!empty page}"><c:if test="${first==1}">?<c:set var="first" value="${0}"/></c:if>page=${page}</c:if><c:if test="${!empty sizePages}"><c:choose><c:when test="${first==1}"><c:set var="first" value="${0}"/>?</c:when><c:otherwise>&</c:otherwise></c:choose>sizePages=${sizePages}</c:if>