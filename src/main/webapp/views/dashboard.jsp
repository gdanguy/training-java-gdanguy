<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="page" %>
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
            <page:listeComputer computer="${computer}"></page:listeComputer>
            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">
        <page:pagination currentPage="${currentPage}" debut="${debut}" fin="${fin}"></page:pagination>
    </div>
    <div  id="pagination" class="btn-group btn-group-sm pull-right" role="group" >
        <a href="/dashboard?page=0&sizePages=10" class="btn btn-default">10</a>
        <a href="/dashboard?page=0&sizePages=50" class="btn btn-default">50</a>
        <a href="/dashboard?page=0&sizePages=100" class="btn btn-default">100</a>
    </div>

</footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>