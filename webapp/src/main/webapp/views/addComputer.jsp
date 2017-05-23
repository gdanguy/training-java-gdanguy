<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="page" %>
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

<page:messageError listError="${messageError}"></page:messageError>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1><spring:message code="dashboard.add" /></h1>
                <form action=
                      <page:link link="/computer/addComputer" path="${pageContext.request.contextPath}"></page:link> method="POST" onsubmit="return validateComputer();">
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName"><spring:message code="dashboard.th.computer" /></label>
                            <input type="text" class="form-control" id="computerName" name="computerName"
                                   placeholder="Computer name" pattern="^[A-Za-z0-9 -]{0,39}[A-Za-z0-9]$" required>
                        </div>
                        <div class="form-group">
                            <label for="introduced"><spring:message code="dashboard.th.intro" /> (Format DD-MM-YYYY)</label>
                            <input type="date" class="form-control" id="introduced" name="introduced"
                                   placeholder="Introduced date"
                                   pattern="^[0-3][0-9][-][0-1][0-9][-](([1][9][9][0-9])|([2-9][0-9]{3}))$">
                        </div>
                        <div class="form-group">
                            <label for="discontinued"><spring:message code="dashboard.th.disco" /> (Format DD-MM-YYYY)</label>
                            <input type="date" class="form-control" id="discontinued" name="discontinued"
                                   placeholder="Discontinued date"
                                   pattern="^[0-3][0-9][-][0-1][0-9][-](([1][9][9][0-9])|([2-9][0-9]{3}))$">
                        </div>
                        <div class="form-group">
                            <label for="companyId"><spring:message code="dashboard.th.company" /></label>
                            <select class="form-control" id="companyId" name="companyId">
                                <option value="-1">--</option>
                                <c:forEach var="company" items="${listCompany}">
                                    <option value="${company.id}">${company.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="Add" class="btn btn-primary">
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