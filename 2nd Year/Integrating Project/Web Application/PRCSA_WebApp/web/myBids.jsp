<%-- 
    Document   : myBids
    Created on : 18-Feb-2015, 22:11:11
    Author     : BrianV
--%>
<!--A page allowing members to view their incoming, outgoing and completed incoming/outgoing bids-->
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

        <title>LETS Plymouth: My Bids</title>

        <c:import url="css.jsp"></c:import>
        </head>
        <body>
            <div id="wrapper">
            <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>

            <c:import url="IncludeJSP/leftNavbarMembers.jsp">
                <c:param name="active" value="myBids"/>
            </c:import>
                <div id="page-content-wrapper">
                    <div class="container-fluid">
                        <div class="row">
                            <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                                <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                            </button>
                            <div class="main">
                                <h1 class="page-header">My Bids</h1>
                                <div class="row"><!--Selecting the correct tab from notification redirects-->
                                    <%!
                                        String incoming, outgoing, accepted, refused = "";
                                    %>
                                    <%
                                        if (request.getParameter("whichBids") != null) {
                                            if (request.getParameter("whichBids").equals("incoming")) {
                                                incoming = "active";
                                            } else if (request.getParameter("whichBids").equals("outgoing")) {
                                                outgoing = "active";
                                            } else if (request.getParameter("whichBids").equals("refused")) {
                                                refused = "active";
                                            } else if (request.getParameter("whichBids").equals("accepted")) {
                                                accepted = "active";
                                            }
                                        } else {
                                            incoming = "active";
                                        }
                                    %>
                                    <div id="content"><!--Tabs for different bid types-->
                                        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                                            <li class="<%= incoming%>"><a href="#incomingBids" data-toggle="tab">Incoming Bids</a></li>
                                            <li class="<%= outgoing%>"><a href="#outgoingBids" data-toggle="tab">Outgoing Bids</a></li>
                                            <li class="<%= accepted%>"><a href="#acceptedBids" data-toggle="tab">Accepted Bids</a></li>
                                            <li class="<%= refused%>"><a href="#refusedBids" data-toggle="tab">Refused Bids</a></li>
                                        </ul>
                                        <div id="my-tab-content" class="tab-content">
                                            <div class="tab-pane <%= incoming%>" id="incomingBids">
                                                <h2 class="sub-header">Incoming Bids</h2>
                                                ${incoming}
                                            </div>
                                            <div class="tab-pane <%= outgoing%>" id="outgoingBids">
                                                <h2 class="sub-header">Outgoing Bids</h2>
                                                ${outgoing}
                                            </div>
                                            <div class="tab-pane <%= accepted%>" id="acceptedBids">
                                                <h2 class="sub-header">Accepted Bids</h2>
                                                ${accepted}
                                            </div>
                                            <div class="tab-pane <%= refused%>" id="refusedBids">
                                                <h2 class="sub-header">Refused Bids</h2>
                                                ${refused}
                                            </div>
                                        </div>
                                    </div>
                                    <%
                                        incoming = outgoing = accepted = refused = "";
                                    %>
                                </div><!--/Row-->
                            </div><!--/Main-->
                        </div><!--/Row-->
                    </div><!--/Container fluid-->
                </div><!--/Page content wrapper-->
            </div><!--/Wrapper-->
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
