<%-- 
    Document   : index
    Created on : 23-Feb-2015, 14:23:27
    Author     : BrianV
--%>
<!--Homepage for guests including a bootstrap carousel-->
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
        <!-- Include all CSS -->
        <c:import url="css.jsp"></c:import>

        </head>

        <body>
            <div id="wrapper">
            <c:import url="IncludeJSP/headerNavbarGuests.jsp"></c:import>
            <c:import url="IncludeJSP/leftNavbarGuests.jsp">
                <c:param name="active" value="home"/>
            </c:import>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row">

                        <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                            <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                        </button>
                    </div></div>
                <div class="carouselAlign">
                    <div id="myCarousel" class="carousel slide" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <div class="item active"><!--Carousel 1-->
                                <div class="col-sm-4">
                                    <img class="first-slide" src="img/lets_logo.png" alt="First slide">
                                </div>
                                <div class="container">
                                    <div class="carousel-caption col-sm-offset-2 col-sm-6">
                                        <h1 class="">Welcome to Plymouth LETS!</h1>
                                        <h4 class="">A system to trade products or services, please sign up to Plymouth LETS today!</h4>
                                        <p><a class="btn btn-lg btn-primary" href="register.jsp" role="button">Sign up today</a></p>
                                    </div>
                                </div>
                            </div><!--/Carousel 1-->
                            <div class="item"><!--Carousel 2-->
                                <div class="col-sm-4">
                                    <img class="first-slide" src="img/lets_logo.png" alt="Second slide">
                                </div>
                                <div class="container">
                                    <div class="carousel-caption col-sm-offset-2 col-sm-6">
                                        <h1 class="">Browse our Products and Services</h1>
                                        <h4 class="">We have a huge number of live adverts both offering and requesting either products or services!</h4>
                                        <p><a class="btn btn-lg btn-primary" href="register.jsp" role="button">Sign up today</a></p>
                                    </div>
                                </div>
                            </div><!--/Carousel 2-->
                            <div class="item"><!--Carousel 3-->
                                <div class="col-sm-4">
                                    <img class="first-slide" src="img/lets_logo.png" alt="Third slide">
                                </div>
                                <div class="container">
                                    <div class="carousel-caption col-sm-offset-2 col-sm-6">
                                        <h1 class="">Up to date info on all our members</h1>
                                        <h4 class="">As part of the LETS scheme, we maintain information on all our members. Please sign up for this and more!</h4>
                                        <p><a class="btn btn-lg btn-primary" href="register.jsp" role="button">Sign up today</a></p>
                                    </div>
                                </div>
                            </div><!--/Carousel 3-->
                        </div><!--/Carousel inner-->
                        <!-- Left and right controls -->
                        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div><!--/Carousel-->
                </div><!--/Carousel alignment-->
            </div><!--/Page content wrapper-->
        </div><!--/Wrapper-->
    </div>
    <!-- Include all JavaScript -->
    <c:import url="javascript.jsp"></c:import>
</body>
</html>
