<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="page" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <jsp:include page="/views/menu.jsp"></jsp:include>
</header>
<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <div class="label label-default pull-right">
                    id: ${id}
                </div>
                <h1>Edit Computer</h1>
                <form action="editComputer" method="POST" modelAttribut="form">
                    <input type="hidden" value="${computer.id}" id="id" name="id"/>
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName">Computer name</label>
                            <input type="text" class="form-control" id="computerName" name="computerName"
                                   value="${computer.name}" pattern="^[A-Za-z0-9 -]{0,39}[A-Za-z0-9]$" required>
                        </div>
                        <div class="form-group">
                            <label for="introduced">Introduced date (Format DD-MM-YYYY)</label>
                            <input type="date" class="form-control" id="introduced" name="introduced"
                                   value="${computer.introduced}"
                                   pattern="^[0-3][0-9][-][0-1][0-9][-](([1][9][9][0-9])|([2-9][0-9]{3}))$">
                        </div>
                        <div class="form-group">
                            <label for="discontinued">Discontinued date (Format DD-MM-YYYY)</label>
                            <input type="date" class="form-control" id="discontinued" name="discontinued"
                                   value="${computer.discontinued}"
                                   pattern="^[0-3][0-9][-][0-1][0-9][-](([1][9][9][0-9])|([2-9][0-9]{3}))$">
                        </div>
                        <div class="form-group">
                            <label for="companyId">Company (Previous : ${computer.companyName})</label>
                            <select class="form-control" id="companyId" name="companyId">
                                <option value="-1">--</option>
                                <c:forEach var="company" items="${listCompany}">
                                    <option value="${company.id}">${company.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="Edit" class="btn btn-primary">
                        or
                        <a href=
                           <page:link link="/dashboard" path="${pageContext.request.contextPath}"></page:link> class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/validate.js"></script>
</body>
</html>