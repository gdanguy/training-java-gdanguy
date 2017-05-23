<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>core.model.Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" rel="stylesheet"
          media="screen">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" type="text/css" rel="stylesheet"
          media="screen">
    <link href="${pageContext.request.contextPath}/css/main.css" type="text/css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <jsp:include page="/views/menu.jsp"></jsp:include>
</header>

<page:messageError listError="${messageError}"></page:messageError>

<section id="main">
    <div class="container">
        <h1 id="homeTitle">
            ${countComputer} <spring:message code="dashboard.find" />
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action=
                <page:link link="/dashboard" path="${pageContext.request.contextPath}"></page:link> method="POST" class="form-inline">
                    <input type="hidden" id="order" name="order" value="${order}">
                    <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name"/>
                    <input type="submit" id="searchsubmit" value="Filter by name"
                           class="btn btn-primary"/>
                </form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer" href=<page:link link="/addComputer" path="${pageContext.request.contextPath}"></page:link>>
                    <spring:message code="dashboard.add" /></a>
                <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">
                    <spring:message code="dashboard.edit" /></a>
            </div>
        </div>
    </div>

    <form id="deleteForm" action=
    <page:link link="/deleteComputer" path="${pageContext.request.contextPath}"></page:link> method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for model.Computer Name -->

                <th class="editMode" style="width: 60px; height: 22px;">
                    <input type="checkbox" id="selectall"/>
                    <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                </th>
                <th>
                    <a href="
                        <c:choose>
                            <c:when test="${!empty order && order == 'name_a'}">
                                <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="${currentPage}" sizePages="${sizePages}" order="name_b"></page:link>">
                            </c:when>
                            <c:otherwise>
                                <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="${currentPage}" sizePages="${sizePages}" order="name_a"></page:link>">
                            </c:otherwise>
                        </c:choose>
                        <spring:message code="dashboard.th.computer" /></a>
                </th>
                <th>
                    <a href="
                    <c:choose>
                        <c:when test="${!empty order && order == 'intro_a'}">
                            <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="${currentPage}" sizePages="${sizePages}" order="intro_b"></page:link>">
                        </c:when>
                        <c:otherwise>
                            <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="${currentPage}" sizePages="${sizePages}" order="intro_a"></page:link>">
                        </c:otherwise>
                    </c:choose>
                    <spring:message code="dashboard.th.intro" /></a>
                </th>
                <!-- Table header for Discontinued Date -->
                <th>
                    <a href="
                    <c:choose>
                        <c:when test="${!empty order && order == 'disco_a'}">
                            <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="${currentPage}" sizePages="${sizePages}" order="disco_b"></page:link>">
                        </c:when>
                        <c:otherwise>
                            <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="${currentPage}" sizePages="${sizePages}" order="disco_a"></page:link>">
                        </c:otherwise>
                    </c:choose>
                    <spring:message code="dashboard.th.disco" /></a>
                </th>
                <!-- Table header for model.Company -->
                <th>
                    <a href="
                    <c:choose>
                        <c:when test="${!empty order && order == 'company_a'}">
                            <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="${currentPage}" sizePages="${sizePages}" order="company_b"></page:link>">
                        </c:when>
                        <c:otherwise>
                            <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="${currentPage}" sizePages="${sizePages}" order="company_a"></page:link>">
                        </c:otherwise>
                    </c:choose>
                    <spring:message code="dashboard.th.company" /></a>
                </th>

            </tr>

            </thead>
            <tbody id="results">
                <!-- Browse attribute computers -->
                <page:listeComputer listComputers="${listComputers}" path="${pageContext.request.contextPath}"></page:listeComputer>
            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">
        <page:pagination path="${pageContext.request.contextPath}" currentPage="${currentPage}" debut="${debut}" fin="${fin}"></page:pagination>
    </div>
    <div id="pagination" class="btn-group btn-group-sm pull-right" role="group">
        <a href=
           <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="0" sizePages="10" order="${order}"></page:link> class="btn btn-default">10</a>
        <a href=
           <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="0" sizePages="50" order="${order}"></page:link> class="btn btn-default">50</a>
        <a href=
           <page:link link="/dashboard" path="${pageContext.request.contextPath}" page="0" sizePages="100" order="${order}"></page:link> class="btn btn-default">100</a>
    </div>

</footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>