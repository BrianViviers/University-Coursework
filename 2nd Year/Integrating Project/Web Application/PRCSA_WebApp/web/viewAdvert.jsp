<%-- 
    Document   : viewAdvert
    Created on : 17-Mar-2015, 13:11:20
    Author     : DominicY
--%>
<!--A page allowing guests to view an advert. No bid or view member controls are available-->
<%@ page import="pagefillers.GetAdvertById" %>
<%@ page import="entities.Advert" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%
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

        <title>LETS Plymouth: View Advert</title>

        <!-- Include all CSS -->
        <c:import url="css.jsp"></c:import>
        </head>
        <body onload="handleTransport()">
            <hr>
        <c:import url="IncludeJSP/headerNavbarGuests.jsp"></c:import>
            <div class="container-fluid"><!--Title, description, transport, expiry date. Jumbotron divider.-->
                <div class="row">
                    <div class="col-xs-6">
                        <div class="jumbotron">
                            <h1 id="parAdTitle"><%=advert.getTitle()%></h1><br>
                        <h4 id="parDescription"><%=advert.getDescription()%></h4><br>
                        <h4 class="label-green" id="parTransport"></h4>
                        <h4 id="parExpiry">Expiry Date: <%=expDate%></h4>
                        </div>
                    </div>
                    <div>
                        <div class="col-xs-2 alert-info"><!--Ad type, item type-->
                            <h4 class="label-green" id="parAdvertType">Advert Type: <b><%=advert.getAdvert_type()%></b></h4>
                            <h4 class="label-green" id="parItemType">Item Type: <b><%=advert.getItem_type()%></b></h4>
                        </div>
                        <div class="col-xs-4  alert-info"><!--Category, credits-->
                            <h4 class="label-green" id="parCategory">Category: <b><%=advert.getCategory()%></b></h4>
                            <h4 class="label-green" id="parCredits">Credits: <b><%=advert.getCost()%></b></h4>
                        </div>
                        <div class="col-xs-6 col-lg-4">
                            <img class="thumb" src="data:image/jpeg;base64,<c:out value="<%=advert.getImage()%>"/>" alt="Advert Image"/>
                        </div>

                        <div class="col-sm-5"><!--Guest controls - back to browse ads, register-->
                            <div class="alert-info">
                                <h2 class="text-primary text-center">What would you like to do next?</h2>
                                <div class="btn-group btn-group-xs col-sm-offset-4" role="group" aria-label="...">
                                    <button type="button" class="btn btn-default" onclick="goBack()">
                                        <img src="img/backx24.png" alt="Cancel"/><br>Back to Adverts</button>
                                    <button type="button" class="btn btn-default" onclick="goRegister()">
                                        <img src="img/registerx24.png" alt="Cancel"/><br>Register for LETS</button>
                                </div>
                                <p class="text-center alert-danger">You must register before you can place a bid.</p>
                            </div>
                        </div>
                    </div>
                </div><!--/Row-->
            </div><!--/Container fluid-->

        <script type='text/javascript'>
        </script>
        <!-- Include all JavaScript -->
        <c:import url="javascript.jsp"></c:import>
        </body>

        <script type="text/javascript">

            // Show transport with explanatory text
            function handleTransport() {
                var transportInc = <%=advert.getTransport()%>;
                if (transportInc === true) {
                    document.getElementById("parTransport").innerHTML = "Transport is included.";
                } else {
                    document.getElementById("parTransport").innerHTML = "Transport is not included.";
                }
            }

            // Return to browse adverts as guest
            function goBack() {
                var url = "BrowseAdverts?no_user=1";
                window.location.replace(url);
            }

            // Redirect to registration page
            function goRegister() {
                var url = "https://localhost:8181/PRCSA_WebApp/register.jsp";
                window.location.replace(url);
            }

    </script>    

</html>
