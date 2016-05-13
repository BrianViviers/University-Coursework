<%-- 
    Document   : memberHome
    Created on : 18-Feb-2015, 13:01:23
    Author     : Brian Viviers
--%>
<!--The member homepage, the member is directed here after login. Shows current adverts-->
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

        <title>Home - LETS Plymouth</title>
        <c:import url="css.jsp"></c:import>
        </head>
        <body>
            <div id="wrapper">
            <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>
            <c:import url="IncludeJSP/leftNavbarMembers.jsp">
                <c:param name="active" value="home"/>
            </c:import>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                            <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                        </button>
                        <div class="main">
                            <div class="row">
                                <h1 class="page-header">LETS Home</h1>

                                <c:if test="${incomingCount > 0}"> <!--Incoming bids notification-->
                                    <div class="col-lg-3 col-md-4">
                                        <div class="panel panel-primary">
                                            <div class="panel-heading">
                                                <div class="row">
                                                    <div class="col-xs-3">
                                                        <i class="fa fa-comments fa-5x"></i>
                                                    </div>
                                                    <div class="col-xs-9 text-right">
                                                        <div class="count-text-size">
                                                            <c:out value="${incomingCount}"/>
                                                        </div>
                                                        <div>of your bids require action</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <a href="MyBids?whichBids=incoming"><!--Redirect to my incoming bids-->
                                                <div class="panel-footer panel-primary-font-color">
                                                    <span class="pull-left">View bids</span>
                                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${outgoingTransactions > 0}"><!--Outgoing transactions notification-->
                                    <div class="col-lg-3 col-md-4">
                                        <div class="panel panel-warning">
                                            <div class="panel-heading">
                                                <div class="row">
                                                    <div class="col-xs-3">
                                                        <i class="fa fa-comments fa-5x"></i>
                                                    </div>
                                                    <div class="col-xs-9 text-right">
                                                        <div class="count-text-size">
                                                            <c:out value="${outgoingTransactions}"/>
                                                        </div>
                                                        <div>of your purchases require action</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <a href="MyTransactions?whichTransaction=outgoing"><!--Redirect to my outgoing transactions-->
                                                <div class="panel-footer count-trans-color">
                                                    <span class="pull-left ">View purchases</span>
                                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </a>
                                        </div>
                                    </div>

                                </c:if>

                                <c:if test="${acceptedCount > 0}"><!--Accepted bids notification-->
                                    <div class="col-lg-3 col-md-4">
                                        <div class="panel panel-accept">
                                            <div class="panel-heading">
                                                <div class="row">
                                                    <div class="col-xs-3">
                                                        <i class="fa fa-thumbs-up fa-5x"></i>
                                                    </div>
                                                    <div class="col-xs-9 text-right">
                                                        <div class="count-text-size">
                                                            <c:out value="${acceptedCount}"/>
                                                        </div>
                                                        <div>of your bids have been accepted since your last login</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <a href="MyBids?whichBids=accepted"><!--Redirect to my accepted bids-->
                                                <div class="panel-footer panel-accept-font">
                                                    <span class="pull-left">View bids</span>
                                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${refusedCount > 0}"><!--Refused bids notification-->
                                    <div class="col-lg-3 col-md-4">
                                        <div class="panel panel-danger">
                                            <div class="panel-heading">
                                                <div class="row">
                                                    <div class="col-xs-3">
                                                        <i class="fa fa-comments fa-5x"></i>
                                                    </div>
                                                    <div class="col-xs-9 text-right">
                                                        <div class="count-text-size">
                                                            <c:out value="${refusedCount}"/>
                                                        </div>
                                                        <div>of your bids have been refused since your last login</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <a href="MyBids?whichBids=refused"><!--Redirect to my refused bids-->
                                                <div class="panel-footer count-refused-color">
                                                    <span class="pull-left ">View bids</span>
                                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>

                        <div class="row"> <!--Notify member of advert creation, bid creation on redirect-->
                            <%
                                String message = (String) session.getAttribute("return_message");
                                session.removeAttribute("return_message");
                                if (message != null) {
                                    if (message.startsWith("Your advert has been created")
                                            || message.startsWith("Your offer has been sent")) {
                            %>
                            <div class="alert alert-success col-sm-3" id="incorrect_pass" role="alert">
                                <%= message.toString()%>
                            </div> 
                            <%
                            } else {
                            %>
                            <div class="alert alert-warning col-sm-3" id="incorrect_pass" role="alert">
                                <%= message.toString()%>
                            </div> 
                            <%
                                    }
                                }
                            %>
                        </div>
                        <div class="row"><!--Showing the member's current adverts-->
                            <h2 class="sub-header">My Current Adverts</h2>
                            <div class="">
                                <div class="table-responsive">
                                    <div class='col-sm-6'>
                                        ${memberAdverts}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><!--/Row-->
                </div><!--/Container-fluid-->
            </div><!--/Page content wrapper-->
        </div><!--/Wrapper-->
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
