<%-- 
    Document   : myAdverts
    Created on : 18-Feb-2015, 22:12:35
    Author     : BrianV
--%>
<!--A page to allow a member to view their own advert. Controls exist to delete the advert here.-->
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

        <title>LETS Plymouth: My Adverts</title>

        <c:import url="css.jsp"></c:import>
        </head>
        <body>
            <div id="wrapper">
            <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>
            <c:import url="IncludeJSP/leftNavbarMembers.jsp">
                <c:param name="active" value="myAdverts"/>
            </c:import>
                <div id="page-content-wrapper">
                    <div class="container-fluid">
                        <div class="row">
                            <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                                <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                            </button>
                            <div class="main">
                                <h1 class="page-header">My Adverts</h1>
                                <div class="row">
                                    <div class="col-xs-6">
                                        <h2 class="sub-header">Current Adverts</h2>
                                        <div class="table-responsive">
                                            ${currentAds}
                                        </div>
                                    </div>
                                    <div class="col-xs-6">
                                        <h2 class="sub-header">Past Adverts</h2>
                                        <div class="table-responsive">
                                            ${pastAds}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div><!--/Row-->
                    </div><!--/Container fluid-->
                </div><!--/Page content wrapper-->
            </div><!--/Wrapper-->
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
