<%-- 
    Document   : createAdvertType
    Created on : 18-Feb-2015, 22:29:26
    Author     : DominicY
--%>
<!--As the first part of the default advert creation process, this page handles the advert type - offer or request - and 
    redirects the member to the item type page. The option to use advanced mode is here.-->
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

            <div class="container-fluid">
                <div class="row">
                    <div class="text-center">
                        <h1 class="page-header">Create an Advert</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="text-center">
                        <h4>Which type of Advert would you like to create?</h4>
                    </div>
                </div>
                <div class="row"><!--Advert type selection-->
                    <div class="text-center col-xs-4 col-sm-4 col-md-4 col-md-offset-1 col-xs-offset-1 col-sm-offset-1">
                        <a href="createAdvertItemType.jsp?type=Offer"><!--Offer-->
                            <img src="img/offerx256.png" alt="Offer" class="img_size_create_advert"/>
                            <h4>Offer</h4></a>
                        <i>An Offer is a product or service you are aiming to sell. <br>For example, you want to sell your games console, or you are offering a tuition service. </i>
                    </div>
                    <div class="text-center col-xs-4 col-sm-4 col-md-4 col-md-offset-1 col-xs-offset-1 col-sm-offset-1">
                        <a href="createAdvertItemType.jsp?type=Request"><!--Request-->
                            <img src="img/requestx256.png" alt="Request" class="img_size_create_advert"/>
                            <h4>Request</h4></a>
                        <i>A Request is a product or service you are looking to buy. <br>For example, you are looking to buy a games console, or you want someone to tutor you in maths.</i>
                    </div>
                </div>
                <div class="row placeholders"><!--Redirect the member to advanced advert creation-->
                    <div class="col-xs-12 col-sm-12 col-md-12">
                        <button type="button" class="btn btn-default" onclick="enterAdvancedMode()">
                            <img src="img/advancedx24.png" alt="Advanced Mode"/><br>Enter Advanced Mode
                        </button>
                    </div>
                </div>
            </div><!--/Container-->

            <script>

                function enterAdvancedMode()
                {
                    window.location.replace("createAdvertAdvanced.jsp");
                }

            </script>
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
