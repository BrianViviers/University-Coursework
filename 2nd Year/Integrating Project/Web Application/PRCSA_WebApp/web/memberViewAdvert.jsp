<%-- 
    Document   : memberHome
    Created on : 18-Feb-2015, 13:01:23
    Author     : DominicY
--%>
<!--The page shown when a member selects an advert to view. NOT their own advert-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="pagefillers.GetAdvertById" %>
<%@ page import="entities.Advert" %>
<%@page import="entities.Member" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>

<%
    Member member = (Member) session.getAttribute("member");
    String addID = request.getParameter("addID").toString();
    Advert advert;
    GetAdvertById object = new GetAdvertById();
    String appRoot = request.getServletContext().getRealPath("/");
    advert = object.GetAdvert(appRoot, addID);

    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
    Date d = sdf.parse(advert.getDate_exp().toString());
    sdf.applyPattern("dd MMM yyyy");
    String expDate = sdf.format(d);
%>
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
        <body onload="handleTransport()">

        <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>
            <hr>
            <div class="container-fluid">
                <div class="row"><!--Showing advert title, description and expiry date-->
                    <div class="col-xs-6">
                        <div class="jumbotron">
                            <h1 id="parAdTitle"><%=advert.getTitle()%></h1><br>
                        <h4 id="parDescription"><%=advert.getDescription()%></h4><br>
                        <h4 class="label-green" id="parTransport"></h4>
                        <h4 id="parExpiry">Expiry Date: <%=expDate%></h4>
                        </div>
                    </div>
                    <div>
                        <div class="col-xs-2 alert-info"><!--Showing advert details - ad type, item type, category, credits-->
                            <h4 class="label-green" id="parAdvertType">Advert Type: <b><%=advert.getAdvert_type()%></b></h4>
                            <h4 class="label-green" id="parItemType">Item Type: <b><%=advert.getItem_type()%></b></h4>
                        </div>
                        <div class="col-xs-4  alert-info">
                            <h4 class="label-green" id="parCategory">Category: <b><%=advert.getCategory()%></b></h4>
                            <h4 class="label-green" id="parCredits">Credits: <b><%=advert.getCost()%></b></h4>
                        </div>

                        <div class="col-xs-6 col-lg-4">
                            <img class="thumb" src="data:image/jpeg;base64,<c:out value="<%=advert.getImage()%>"/>" alt="Advert Image"/>
                        </div>
                    </div>
                    <div class="col-sm-5"> <!--Control panel - members can return to browse ads, view the advert's member or make a bid-->
                        <div class="alert-info">
                            <h2 class="text-primary text-center">What would you like to do next?</h2>
                            <div class="btn-group btn-group-xs col-sm-offset-2" role="group" aria-label="...">
                                <form method="post" style="display: inline" class="navbar-form" action="" >
                                    <button type="button" class="btn btn-default" onclick="goBack()">
                                        <img src="img/backx24.png" alt="Cancel"/><br>Back to Browse</button>
                                    <button type="button" class="btn btn-default" onclick="viewMember()">
                                        <img src="img/accountx24.png" alt="Cancel"/><br>View Member</button>
                                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#respondModal">
                                        <img src="img/bidx24.png" alt="Cancel"/><br>Make a Bid</button>

                                </form>
                            </div>
                        </div>
                    </div>
                    <!-- Modal to make a bid on an Advert -->
                    <div class="modal fade" id="respondModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="myModalLabel">Make a Bid</h4>
                                </div>
                                <%
                                    if (member.getBalance() - advert.getCost() < 0) {
                                %>
                                <h4>You do not have enough credits to make a bid!</h4>
                                <%
                                } else {
                                %>
                                <form method="post" action="RespondToAdvert">
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label class="control-label col-xs-3 text-info">Enter a message:</label>
                                            <div class="input-group col-xs-9">
                                                <textarea class="form-control" id="message" name="message" placeholder="State any conditions you have such as date and time etc." required></textarea>
                                            </div>
                                        </div>
                                        <input type="text" name="advertID" value="<%=advert.getId()%>" hidden/>
                                        <input type="text" name="memberID" value="<%=member.getId()%>" hidden/>
                                        <input type="text" name="advertOwnerID" value="<%=advert.getMember_id()%>" hidden/>

                                        <%
                                            int advertTypeID;
                                            if (advert.getAdvert_type().equalsIgnoreCase("offer")) {
                                                advertTypeID = 1;
                                            } else {
                                                advertTypeID = 2;
                                            }
                                        %>
                                        <input type="text" name="advertTypeID" value="<%=advertTypeID%>" hidden/>
                                        <p class="alert-danger text-center">As soon as your bid is accepted, your credits will be deducted.</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-default">Send</button>
                                    </div>
                                </form>
                                <%
                                    }
                                %>
                            </div><!--/Modal content-->
                        </div><!--/Modal dialog-->
                    </div><!--/Modal-->
                </div><!--/Row-->
            </div><!--/Container fluid-->
        <c:import url="javascript.jsp"></c:import>
        </body>
        <script type="text/javascript">

            function handleTransport() {
                var transportInc = <%= request.getParameter("Transport")%>;
                if (transportInc === true) {
                    document.getElementById("parTransport").innerHTML = "Transport is included.";
                } else {
                    document.getElementById("parTransport").innerHTML = "Transport is not included.";
                }
            }

            function goBack() {
                var url = "BrowseAdverts";
                window.location.replace(url);
            }

            function viewMember() {
                var url = "MemberReviews?memberID=<%=advert.getMember_id()%>";
                window.location.replace(url);
            }

    </script>    
</html>
