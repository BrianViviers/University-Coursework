<%-- 
    Document   : guestBrowseAdverts
    Created on : 18-Feb-2015, 22:08:10
    Author     : BrianV
--%>
<!--A page to display a list of current adverts to guests-->
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

        <title>LETS Plymouth: Search Adverts</title>
        <!-- Include all CSS -->
        <c:import url="css.jsp"></c:import>
        </head>
        <body>
            <div id="wrapper">
            <c:import url="IncludeJSP/headerNavbarGuests.jsp"></c:import>
            <c:import url="IncludeJSP/leftNavbarGuests.jsp">
                <c:param name="active" value="adverts"/>
            </c:import>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                            <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                        </button>
                        <div class="main"><br>
                            <h1 class="page-header">Browse Adverts</h1>
                            <div class="row">
                                <div class="col-xs-6"><!--Advert list-->
                                    ${adverts}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/Page content-->
        </div><!--/Wrapper-->
        <!-- Include all JavaScript -->
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
