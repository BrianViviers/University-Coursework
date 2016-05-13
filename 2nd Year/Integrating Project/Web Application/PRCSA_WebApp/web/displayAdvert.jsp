<%-- 
    Document   : displayAdvert
    Created on : 18-Feb-2015, 22:30:15
    Author     : DominicY
--%>
<!--The final stage of advert creation - this page forms a preview of the advert created. The member 
    can upload, return to edit, or discard their advert here.-->
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

        <title>LETS Plymouth</title>

        <c:import url="css.jsp"></c:import>
        </head>

        <body>
        <c:import url="IncludeJSP/headerCreateAdverts.jsp"></c:import>

            <div class="container">
                <div class="row">
                    <div>
                        <h1 class="page-header">Your Advert</h1>
                    </div>    
                    <!--Displaying all advert details in preview-->
                    <div class="col-xs-6">
                        <div class="jumbotron">
                            <h1 id="parAdTitle">${textTitle}</h1><br>
                        <h4 id="parDescription">${textDescription}</h4><br>
                        <h4 class="label-green" id="parTransport">${transport}</h4>
                    </div>
                </div>
                <div>
                    <div class="col-xs-2 alert-info">
                        <h4 class="label-green" id="parAdvertType">Advert Type: ${advertType}</h4>
                        <h4 class="label-green" id="parItemType">Item Type: ${itemType}</h4>
                    </div>
                    <div class="col-xs-4  alert-info">
                        <h4 class="label-green" id="parCategory">Category: ${category}</h4>
                        <h4 class="label-green" id="parCredits">Credits: ${textCredits}</h4>
                    </div>
                    <div class="col-xs-6">
                        <div class="col-xs-6 col-lg-4">
                            <img class="thumb" src="${image}" alt=""/>
                        </div>
                    </div>
                    <div class="col-sm-6"><!--Controls to edit, discard or publish advert-->
                        <div class="alert-info">
                            <div class="btn-group btn-group-xs col-sm-offset-2" role="group" aria-label="...">
                                <h2 class="text-primary text-center">What would you like to do next?</h2>
                                <form method="post" style="display: inline" class="navbar-form" action="CreateAdvert" >
                                    <button type="button" class="btn btn-default" onclick="cancelAdvert()">
                                        <img src="img/nox24.png" alt="Cancel"/><br>Discard Advert</button>
                                    <button type="button" class="btn btn-default"  onclick="editAdvert()">
                                        <img src="img/editx24.png" alt="Edit"/><br>Edit Advert</button>
                                    <button type="submit" class="btn btn-default">
                                        <img src="img/createx24.png" alt="Publish"/><br>Publish Advert</button>
                                    <br><br><label class="text-center label-danger" id="parAuctionEnd">Your advert will be shown for two weeks or until a bid is accepted.</label>
                                    <!--Hidden form containing all advert details-->
                                    <input type="text" id="title" name="title" value="${textTitle}" hidden/>
                                    <input type="text" id="description" name="description" value="${textDescription}" hidden/>
                                    <input type="text" id="credits" name="credits" value="${textCredits}" hidden/>
                                    <input type="text" id="category" name="category" value="${category}" hidden/>
                                    <input type="text" id="advert_type" name="advert_type" value="${advertType}" hidden/>
                                    <input type="text" id="item_type" name="item_type" value="${itemType}" hidden/>
                                    <input type="text" id="image" name="image" value="${image}" hidden/>
                                    <input type="text" id="transport" name="transport" value="${transportBool}" hidden/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/Row-->
            <hr>
            <footer>
                <p>&copy; Plymouth LETS 2015</p>
            </footer>

        </div><!--/Container-->

        <script type='text/javascript'>
            // Returning the member to advanced creation page to edit their advert. Setting up URL query for redirection and notifying user
            // any uploaded images will be lost.
            function editAdvert()
            {
                if (confirm("Are you sure you want to leave this page to edit your advert? Your image will be lost. \n"
                        + "Click OK to edit your advert, Cancel to stay here."))
                {
                    var url = "createAdvertAdvanced.jsp?Type=" + '<c:out value='${advertType}'/>'
                            + "&Item=" + '<c:out value='${itemType}'/>'
                            + "&Category=" + '<c:out value='${category}'/>'
                            + "&Credits=" + '<c:out value='${textCredits}'/>'
                            + "&Transport=" + '<c:out value='${transportBool}'/>'
                            + "&Title=" + '<c:out value='${textTitle}'/>'
                            + "&Description=" + '<c:out value='${textDescription}'/>';
                    var res = encodeURI(url);
                    window.location.replace(res);
                }
            }

            // Discarding the advert. After notifying user, returning to homepage.
            function cancelAdvert()
            {
                var url = "MemberHome";
                if (confirm("Are you sure you want to leave this page? Your advert will be lost. Click OK to return to the homepage."))
                {
                    window.location.replace(url);
                }
            }
        </script>
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
