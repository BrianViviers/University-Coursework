<%-- 
    Document   : browseAdverts
    Created on : 18-Feb-2015, 15:09:26
    Author     : Brian Viviers
--%>
<!--A page to display a list of current adverts to members-->
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

        <title>LETS Plymouth: Search Adverts</title>

        <c:import url="css.jsp"></c:import>
        </head>

        <body>
            <div id="wrapper"><!--Wrapper-->
            <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>


            <c:import url="IncludeJSP/leftNavbarMembers.jsp">
                <c:param name="active" value="browseAds"/>
            </c:import>
            <div id="page-content-wrapper"><!--Page-->
                <div class="container-fluid"><!--Container-->
                    <div class="row"><!--Row-->
                        <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                            <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                        </button>
                        <div class="main"><!--Main Div-->
                            <h1 class="page-header">Browse Adverts</h1>
                            <div class="row">
                                <div class="col-sm-6"><!--Advert List-->
                                    ${adverts}
                                </div><!--/Advert List-->
                            </div>

                        </div><!--/Main Div-->
                    </div><!--/Row-->
                </div><!--/Container-->
            </div><!--/Page-->
        </div><!--/Wrapper-->
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
