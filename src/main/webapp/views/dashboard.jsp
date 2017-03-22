<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" type="text/css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/main.css" type="text/css" rel="stylesheet" media="screen">

</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <jsp:include page="/views/menu.jsp"></jsp:include>
</header>

<section id="main">
    <div class="container">
        <h1 id="homeTitle">
            ${countComputer} Computers found
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="/dashboard" method="POST" class="form-inline">
                    <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                    <input type="submit" id="searchsubmit" value="Filter by name"
                           class="btn btn-primary" />
                </form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer" href="/addComputer">Add Computer</a>
                <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="/deleteComputer" method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->

                <th class="editMode" style="width: 60px; height: 22px;">
                    <input type="checkbox" id="selectall" />
                    <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                </th>
                <th>
                    Computer name
                </th>
                <th>
                    Introduced date
                </th>
                <!-- Table header for Discontinued Date -->
                <th>
                    Discontinued date
                </th>
                <!-- Table header for Company -->
                <th>
                    Company
                </th>

            </tr>

            </thead>

            <!-- Browse attribute computers -->
            <c:forEach var="computer"  items="${listComputers}" >
                <tr>
                    <td class="editMode">
                        <input type="checkbox" name="cb${computer.id}" class="cb" value="${computer.id}">
                    </td>
                    <td>
                        <a href="/editComputer?id=${computer.id}" onclick="">${computer.name}</a>
                    </td>
                    <td>${computer.introduced}</td>
                    <td>${computer.discontinued}</td>
                    <td>${computer.companyName}</td>

                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">
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
            <c:if test="${fin > currentPage}">
            <li>
                <a href="/dashboard?currentPage=${currentPage+1}&sizePages=${sizePages}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
            </c:if>
        </ul>
    </div>
    <div  id="pagination" class="btn-group btn-group-sm pull-right" role="group" >
        <form action="/dashboard?currentPage=${currentPage}&sizePages=10" method="post">
            <button type="submit" class="btn btn-default">10</button>
        </form>
        <form action="/dashboard?currentPage=${currentPage}&sizePages=50" method="post">
            <button type="submit" class="btn btn-default">50</button>
        </form>
        <form action="/dashboard?currentPage=${currentPage}&sizePages=100" method="post">
            <button type="submit" class="btn btn-default">100</button>
        </form>
    </div>

</footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>