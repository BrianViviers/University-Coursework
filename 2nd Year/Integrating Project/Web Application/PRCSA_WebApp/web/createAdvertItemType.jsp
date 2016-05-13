<%-- 
    Document   : createAdvertItemType
    Created on : 18-Feb-2015, 22:28:21
    Author     : DominicY
--%>
<!--As part of the default advert creation process, this page handles the item type and redirects the member
    to the item type page-->
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

    <body onload="onload()">
        <c:import url="IncludeJSP/headerCreateAdverts.jsp"></c:import>

        <div class="container-fluid">
            <div class="row">
                <div class="text-center">
                    <h1 class="page-header">Create an Advert</h1>
                </div>
            </div>
            <div class="row">
                <div class="text-center">
                    <h4 id="parTitle"></h4>
                </div>
            </div>
            <div class="row"><!--Selecting the item type-->
                <div class="text-center col-xs-4 col-sm-4 col-md-4 col-md-offset-1 col-xs-offset-1 col-sm-offset-1">
                    <a id="aProduct" href="createAdvertItemType.jsp?type=Offer"><!--Product-->
                        <img src="img/productx256.png" alt="Product" class="img_size_create_advert"/>
                        <h4>Product</h4></a>
                    <i>A Product is an actual item. For example, a games console or a fridge freezer.</i>
                </div>
                <div class="text-center col-xs-4 col-sm-4 col-md-4 col-md-offset-1 col-xs-offset-1 col-sm-offset-1">
                    <a id="aService" href="createAdvertItemType.jsp?type=Request"><!--Service-->
                        <img src="img/servicex256.png" alt="Service" class="img_size_create_advert"/>
                        <h4>Service</h4></a>
                    <i>A Service is act or piece of work. For example, lawn-mowing, tutoring or walking someone's dog.</i>
                </div>
            </div>
        </div><!--/Container-->        

        <script type='text/javascript'>

            var query;

            // Context sensitive title updating and handling page redirects
            function onload() {
                query = getQueryParams(document.location.search);
                var url1 = "createAdvertCategory.jsp?type=" + query.type;
                var url2 = "createAdvertCategory.jsp?type=" + query.type;
                url1 += "&Item=Product";
                url2 += "&Item=Service";
                document.getElementById("aProduct").href = url1;
                document.getElementById("aService").href = url2;
                // Inserting the word "offer" or "request" in information text.
                document.getElementById("parTitle").innerHTML = "Which type of item would you like to " + query.type + "?"
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

        </script>  
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
