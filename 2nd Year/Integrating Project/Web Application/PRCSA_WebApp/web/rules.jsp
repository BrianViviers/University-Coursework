<%-- 
    Document   : rules
    Created on : 18-Feb-2015, 22:09:08
    Author     : BrianV
--%>
<!--A page showing the rules. This is a member page so member navbars are included-->
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

        <title>LETS Plymouth: Rules</title>

        <c:import url="css.jsp"></c:import>
        </head>

        <body>
            <div id="wrapper">
            <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>

            <c:import url="IncludeJSP/leftNavbarMembers.jsp">
                <c:param name="active" value="rules"/>
            </c:import>
                <div id="page-content-wrapper">
                    <div class="container-fluid">
                        <div class="row">
                            <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                                <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                            </button>
                            <div class="col-sm-6 main">
                                <h1 class="page-header">Rules</h1>
                                <div class="container-fluid"><!--Rules list-->
                                    ${rules}
                                </div>
                            </div>
                        </div>
                    </div><!--/Row-->
                </div><!--/Page content wrapper-->
            </div><!--/Wrapper-->
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
