<%-- 
    Document   : searchMembers
    Created on : 18-Feb-2015, 15:09:26
    Author     : Brian Viviers
--%>
<!--A page showing the list of all active members-->
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

        <title>LETS Plymouth: Search Members</title>

        <c:import url="css.jsp"></c:import>
        </head>
        <body>
            <div id="wrapper">
                <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>
                <c:import url="IncludeJSP/leftNavbarMembers.jsp">
                    <c:param name="active" value="findMembers"/>
                </c:import>
                <div id="page-content-wrapper">
                    <div class="container-fluid">
                        <div class="row">
                            <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                                <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                            </button>
                            <div class="main">
                                <h1 class="page-header">Find Members</h1>
                                <div class="col-sm-9 col-lg-7">
                                    <div class="row placeholders width100Percent">
                                        <form class="navbar-form" role="search" action="SearchMembers">
                                            <div class="input-group width100Percent">
                                                <input type="text" style="height:50px" name="searchedMember" class="form-control col-sm-6 width100Percent" placeholder="Find a member..." >
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" style="height:50px;float:left" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="row">
                                        ${members}
                                    </div>
                                </div>
                            </div><!--/Main-->
                        </div><!--/Row-->
                    </div><!--/Container fluid-->
                </div><!--/Page content wrapper-->
            </div><!--/Wrapper-->
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
