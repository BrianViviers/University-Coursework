<%-- 
    Document   : rules
    Created on : 18-Feb-2015, 22:09:08
    Author     : BrianV
--%>
<!--A member page showing the reviews and further member details when selected-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="../../favicon.ico">

        <title>LETS Plymouth: Member Reviews</title>

        <c:import url="css.jsp"></c:import>
        </head>
        <body>
        <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="main">
                        <div class="row">
                            <h1 class="page-header">${memberName}</h1>
                        <div class="container-fluid">
                            ${reviews}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>