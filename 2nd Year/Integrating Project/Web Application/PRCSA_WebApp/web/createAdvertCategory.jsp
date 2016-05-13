<%-- 
    Document   : createAdvertCategory
    Created on : 18-Feb-2015, 22:25:49
    Author     : DominicY
--%>
<!--As part of the normal advert creation, this contains a list of categories which, when selected, store the item category 
    and redirect the member to the input advert details page-->
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

    <body onload="onload();">
        <c:import url="IncludeJSP/headerCreateAdverts.jsp"></c:import>

        <div class="container-fluid text-center"><!--Page container-->
            <div class="row">
                <div class="text-center">
                    <h1 class="page-header">Create an Advert</h1>

                    <div class="row placeholders"><!--Advert Title-->
                        <div class="col-md-offset-4 col-sm-offset-3 col-sm-6 col-md-4">
                            <h4 id="parTitle"></h4>
                        </div>
                    </div>
                    <div class="row placeholders">
                        <div class="col-md-offset-4 col-sm-offset-3 col-sm-6 col-md-4">
                            <ul class="list-group"><!--A list of links to the input advert details page. Item category selection-->
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Accounting');" href="#"><img src="img/accountingx24.png"><br>Accounting</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Cleaning');" href="#"><img src="img/cleaningx24.png"><br>Cleaning</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Computing and Electronics');" href="#"><img src="img/electronicsx24.png"><br>Computing & Electronics</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Education');" href="#"><img src="img/educationx24.png"><br>Education</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Health');" href="#"><img src="img/healthx24.png"><br>Health</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Home and Garden');" href="#"><img src="img/homeGardenx24.png"><br>Home & Garden</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Maintenance');" href="#"><img src="img/maintenancex24.png"><br>Maintenance</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Printing');" href="#"><img src="img/printingx24.png"><br>Printing</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Recreation');" href="#"><img src="img/recreationx24.png"><br>Recreation</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Transport');" href="#"><img src="img/transportx24.png"><br>Transport</a></li>
                                <li class="list-group-item"><a class="list-group-item" onclick="redirectPage('Other');" href="#"><img src="img/otherx24.png"><br>Other</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div><!--/Row-->
        </div><!--/Page container-->

        <script type='text/javascript'>

            var query;

            // Accessing the URL query to fill in advert title heading
            function onload() {
                query = getQueryParams(document.location.search);
                document.getElementById("parTitle").innerHTML = "In which category is the " + query.Item + " you are " + query.type + "ing?";
            }
            
            // Accessing URL query and storing in an object
            function getQueryParams(qs) {
                qs = qs.split("+").join(" ");

                var params = {}, tokens,
                        re = /[?&]?([^=]+)=([^&]*)/g;

                while (tokens = re.exec(qs)) {
                    params[decodeURIComponent(tokens[1])]
                            = decodeURIComponent(tokens[2]);
                }

                return params;
            }

            // Redirect member to advert details input page. Appending advert type, item type and item category to URL.
            function redirectPage(test) {
                var url = "createAdvertDetails.jsp?Type=" + query.type + "&Item=" + query.Item + "&Category=" + test;
                window.location.replace(url);
            }


        </script>  
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>

