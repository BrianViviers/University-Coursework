<%-- 
    Document   : memberHome
    Created on : 18-Feb-2015, 13:01:23
    Author     : DominicY
--%>
<!--A page allowing members to view their own advert. Controls to delete advert are here.-->
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

        <title>LETS Plymouth</title>
        <c:import url="css.jsp"></c:import>
        </head>
        <body onload="handleTransport()">

        <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>
            <div class="container-fluid">
                <div class="row">
                    <div>
                        <h1 class="page-header">Your Advert</h1>
                    </div>    

                    <div class="col-xs-6"><!--Jumbotron - title, description, expiry date-->
                        <div class="jumbotron">
                            <h1 id="parAdTitle"><c:out value="${advert.title}" /></h1><br>
                        <h4 id="parDescription"><c:out value="${advert.description}" /></h4><br>
                        <h4 class="label-green" id="parTransport"></h4>
                        <%@page import="java.text.SimpleDateFormat" %>
                        <%@page import="java.util.Date" %>
                        <%@page import="entities.Advert" %>
                        <%
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                            Advert advert = (Advert) request.getAttribute("advert");
                            Date d = sdf.parse(advert.getDate_exp().toString());
                            sdf.applyPattern("dd MMM yyyy");
                            String expDate = sdf.format(d);
                        %>
                        <h4 id="parExpiry">Expiry Date: <%=expDate%></h4>
                    </div>
                </div>
                <div>
                    <div class="col-xs-2 alert-info"><!--Advert type, item type-->
                        <h4 class="label-green" id="parAdvertType">Advert Type: <b>${advert.advert_type}</b></h4>
                        <h4 class="label-green" id="parItemType">Item Type: <b>${advert.item_type}</b></h4>
                    </div>
                    <div class="col-xs-4  alert-info"><!--Category, credits-->
                        <h4 class="label-green" id="parCategory">Category: <b>${advert.category}</b></h4>
                        <h4 class="label-green" id="parCredits">Credits: <b>${advert.cost}</b></h4>
                    </div>
                    <div class="col-xs-6 col-lg-4"><!--Advert image display-->
                        <img class="thumb" src="data:image/jpeg;base64,<c:out value="${advert.image}"/>" alt="Advert Image"/>
                    </div>
                </div>
                <div class="col-sm-5">
                    <div class="alert-info"><!--Member controls - return to my adverts, delete advert-->
                        <h2 class="text-primary text-center">What would you like to do next?</h2>
                        <div class="btn-group btn-group-xs col-sm-offset-2" role="group" aria-label="...">
                            <form method="post" style="display: inline" class="navbar-form" onsubmit="return confirmDelete()" action="RemoveAdvert" >
                                <button type="button" class="btn btn-default" onclick="goBack()">
                                    <img src="img/backx24.png" alt="Cancel"/><br>Back to My Adverts</button>
                                <button type="submit" class="btn btn-default">
                                    <img src="img/nox24.png" alt="Cancel"/><br>Delete Advert</button>
                                <input type="text" name="advertID" value="${advert.id}" hidden/>
                            </form>
                        </div>
                    </div>
                </div>
            </div><!--/Row-->
            <hr>
            <div class="row col-sm-offset-2 col-sm-8"><!--Advert bids-->
                <h2 class="sub-header">Bids on advert</h2>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Advert Title</th>
                                <th>Message</th>
                                <th>Bid Date</th>
                                <th>Member Name</th>
                                <th>Accept/Reject</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${bids}
                        </tbody>
                    </table>
                </div>
            </div>
        </div><!--/Container fluid-->

        <!-- Modal to say why refusing a bid -->
        <div class="modal fade" id="refuseModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Respond to bid</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label col-xs-3 text-info">Enter a message:</label>
                            <div class="input-group col-xs-9">
                                <textarea form="acceptReject" class="form-control" id="return_message_reject" name="return_message_reject" placeholder="State why you are refusing the bid..." required></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="submit" onclick='return setBidIDReject()' name="action" form="acceptReject" class="btn btn-default" value="Reject">Send</button>
                    </div>
                </div>
            </div>
        </div><!--Refuse bid modal-->

        <!-- Modal to say why accepting a bid -->
        <div class="modal fade" id="acceptModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Respond to bid</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label col-xs-3 text-info">Enter a message:</label>
                            <div class="input-group col-xs-9">
                                <textarea form="acceptReject" class="form-control" id="return_message_accept" name="return_message_accept" 
                                          placeholder="If necessary, please suggest a good time for the member to contact you..." required></textarea>
                            </div>
                        </div>
                        <p class="alert-danger text-center">You will receive your credits when the advert is confirmed by the other member.<br>Your contact info will be sent.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="submit" name="action" form="acceptReject" class="btn btn-default" value="Accept">Send</button>
                    </div>
                </div>
            </div>
        </div><!--/Accept bid modal-->
        <input type='text' id='bidIDAccept' form="acceptReject" name='bidIDAccept' value='' hidden/>
        <input type='text' id='bidIDReject' form="acceptReject" name='bidIDReject' value='' hidden/>

        <c:import url="javascript.jsp"></c:import>
            <script type="text/javascript">
                // Show transport as explanatory text
                function handleTransport() {
                    var transportInc = <%= request.getParameter("Transport")%>;
                    if (transportInc === true) {
                        document.getElementById("parTransport").innerHTML = "Transport is included.";
                    } else {
                        document.getElementById("parTransport").innerHTML = "Transport is not included.";
                    }
                }
                // Redirect member to their adverts
                function goBack() {
                    var url = "MyAdverts";
                    window.location.replace(url);
                }

                // Alert member they are about to delete their advert
                function confirmDelete() {
                    if (confirm("Are you sure you want to delete this advert?")) {
                        return true;
                    }
                    return false;
                }
                
                // Sets the bid ID of the selected bid to send with the form
                function SetBidIDAccept(ID) {
                    document.getElementById("bidIDAccept").value = "";
                    console.log(ID);
                    document.getElementById("bidIDAccept").value = ID;
                }
                
                // Sets the bid ID of the selected bid to send with the form
                function SetBidIDReject(ID) {
                    document.getElementById("bidIDReject").value = "";
                    console.log(ID);
                    document.getElementById("bidIDReject").value = ID;
                }
        </script>
    </body>
</html>
