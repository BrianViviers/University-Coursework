<%-- 
    Document   : createAdvertType
    Created on : 18-Feb-2015, 22:29:26
    Author     : BrianV
--%>
<!--Server error page-->
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
        <link rel="icon" href="../favicon.ico">

        <title>LETS Plymouth</title>

        <c:import url="css.jsp"></c:import>
        </head>

        <body>
        <c:import url="IncludeJSP/headerNavbarGuests.jsp"></c:import>

            <div class="container-fluid">
                <div class="row">
                    <div class="text-center">
                        <h1>There was a server error!</h1>
                    </div>
                </div>
            </div>
            <c:import url="javascript.jsp"></c:import>
    </body>
</html>
