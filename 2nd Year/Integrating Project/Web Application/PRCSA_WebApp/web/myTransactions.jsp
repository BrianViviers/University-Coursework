<%-- 
    Document   : myTransactions
    Created on : 18-Feb-2015, 22:10:29
    Author     : BrianV
--%>
<!--A page allowing members to view their transactions - incoming, outgoing and completed incoming/outgoing-->
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
        <link rel="stylesheet" href="css/StarRating.css">

        <title>LETS Plymouth: My Transactions</title>

        <c:import url="css.jsp"></c:import>
        </head>
        <body>
            <div id="wrapper">
            <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>
            <c:import url="IncludeJSP/leftNavbarMembers.jsp">
                <c:param name="active" value="myTransactions"/>
            </c:import>
                <div id="page-content-wrapper" style="margin-left: -20px">
                    <div class="container-fluid">
                        <div class="row">
                            <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                                <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                            </button>
                            <div class="main">
                                <h1 class="page-header">My Transactions</h1>
                                <div class="row"><!--Selecting the correct tab from notification redirects-->
                                    <%!
                                        String incoming, outgoing, compIncoming, compOutgoing = "";
                                    %>
                                    <%
                                        if (request.getParameter("whichTransaction") != null) {
                                            if (request.getParameter("whichTransaction").equals("incoming")) {
                                                incoming = "active";
                                            } else if (request.getParameter("whichTransaction").equals("outgoing")) {
                                                outgoing = "active";
                                            } else if (request.getParameter("whichTransaction").equals("compincoming")) {
                                                compIncoming = "active";
                                            } else if (request.getParameter("whichTransaction").equals("compoutgoing")) {
                                                compOutgoing = "active";
                                            }
                                        } else {
                                            incoming = "active";
                                        }
                                    %>
                                    <div id="content"><!--Tabs for different transaction types-->
                                        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                                            <li class="<%= incoming%>"><a href="#incomingTransactions" data-toggle="tab">Selling</a></li>
                                            <li class="<%= compIncoming%>"><a href="#compIncomingTransactions" data-toggle="tab">Sold</a></li>
                                            <li class="<%= outgoing%>"><a href="#outgoingTransactions" data-toggle="tab">Buying</a></li>
                                            <li class="<%= compOutgoing%>"><a href="#compOutgoingTransactions" data-toggle="tab">Bought</a></li>
                                        </ul>
                                        <div id="my-tab-content" class="tab-content">
                                            <div class="tab-pane <%= incoming%>" id="incomingTransactions">
                                                <h2 class="sub-header">Selling Transactions</h2>
                                                ${incoming}
                                            </div>
                                            <div class="tab-pane <%= compIncoming%>" id="compIncomingTransactions">
                                                <h2 class="sub-header">Sold Transactions</h2>
                                                ${incomingCompleted}
                                            </div>
                                            <div class="tab-pane <%= outgoing%>" id="outgoingTransactions">
                                                <h2 class="sub-header">Buying Transactions</h2>
                                                ${outgoing}
                                            </div>
                                            <div class="tab-pane <%= compOutgoing%>" id="compOutgoingTransactions">
                                                <h2 class="sub-header">Bought Transactions</h2>
                                                ${outgoingCompleted}
                                            </div>
                                        </div>
                                    </div>
                                    <%
                                        incoming = outgoing = compIncoming = compOutgoing = "";
                                    %>
                                </div><!--/Row-->
                            </div><!--/Main-->
                        </div><!--/Row-->
                    </div><!--/Container fluid-->
                </div><!--/Page content wrapper-->
            </div><!--/Wrapper-->
            <!--Modal to enter a review and respond to a bid-->
            <div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title text-center">Review your Transaction</h4>
                        </div>
                        <form method="post" id="enterReview" action="FinishTransaction">
                            <div class="modal-body">
                                <div class="form-group text-center">
                                    <h4 class="modal-title">Rating:
                                        <span class="starRating">
                                            <input id="rating5" type="radio" name="rating" value="5">
                                            <label for="rating5">5</label>
                                            <input id="rating4" type="radio" name="rating" value="4">
                                            <label for="rating4">4</label>
                                            <input id="rating3" type="radio" name="rating" value="3" checked>
                                            <label for="rating3">3</label>
                                            <input id="rating2" type="radio" name="rating" value="2">
                                            <label for="rating2">2</label>
                                            <input id="rating1" type="radio" name="rating" value="1">
                                            <label for="rating1">1</label>
                                        </span>
                                    </h4>
                                    <div class="form-group">
                                        <br><label class="control-label col-xs-3 text-info">Enter your review text:</label>
                                        <div class="input-group col-xs-9">
                                            <textarea form="enterReview" class="form-control" id="review_text" name="review_text" placeholder="Please detail your experience with the other member..." required></textarea>
                                        </div>
                                    </div>
                                    <input type="text" name="transactionID" id="transactionID" hidden/>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <button type="submit" form="enterReview" class="btn btn-default">Send</button>
                            </div>
                        </form>
                    </div><!--/Modal content-->
                </div><!--/Modal dialog-->
            </div><!--/Modal-->
        <c:import url="javascript.jsp"></c:import>
        <script>
            function SetTransactionID(ID) {
                document.getElementById("transactionID").value = ID;
            }
        </script>
    </body>
</html>
