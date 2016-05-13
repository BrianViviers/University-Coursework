<%-- 
    Document   : help
    Created on : 18-Feb-2015, 22:13:15
    Author     : BrianV
--%>
<!--A page to show help to members. Different navbars are included-->
<%@ page import="entities.Member" %>
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

        <title>LETS Plymouth: Help</title>

        <c:import url="css.jsp"></c:import>
        </head>
        <body>
            <div id="wrapper">
            <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>
            <c:import url="IncludeJSP/leftNavbarMembers.jsp">
                <c:param name="active" value="help"/>
            </c:import>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                            <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                        </button>
                        <div class="main"><br>
                            <h1 class="page-header">Welcome to LETS!</h1>
                            <div class="row"><!--Email admin sent/failed notifications-->
                                <%
                                    String message = (String) session.getAttribute("return_message");
                                    session.removeAttribute("return_message");
                                    if (message != null) {
                                        if (message.startsWith("Your email has been sent")) {
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
                            <div class="row"><!--Hard-coded help list-->
                                <div class="col-md-6 col-sm-6">
                                    <h2>What is LETS?</h2>
                                    <p>LETS is a community trading service. Members can create adverts to either offer their products or services, or request them. Guests are only allowed basic navigation and cannot make offers or adverts. 
                                        A number of rules can be viewed using links to the left. </p><br><br>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-md-4">
                                    <h2>What is an Offer?</h2>
                                    <p>An Offer is a product or service you are aiming to sell.<br>For example, you want to sell your games console, or you are offering a tuition service.</p><br><br>
                                </div>
                                <div class="col-sm-6 col-md-4">
                                    <h2>What is a Request?</h2>
                                    <p>A Request is a product or service you are looking to buy. <br>For example, you are looking to buy a games console, or you want someone to tutor you in maths.</p><br><br>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-md-4">
                                    <h2>What is a Product?</h2>
                                    <p>A Product is an actual item. For example, a games console or a fridge freezer.</p><br><br>
                                </div>
                                <div class="col-sm-6 col-md-4">
                                    <h2>What is a Service?</h2>
                                    <p>A Service is act or piece of work. For example, lawn-mowing, tutoring or walking someone's dog.</p><br><br><br>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 col-sm-6">
                                    <h2>Contact an Admin</h2>
                                    <p>Any issues, please use the button below to email an administrator.</p>
                                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#emailModal">
                                        <img src="img/emailx24.png" alt="email"/><br>Email Admin</button>
                                </div>
                            </div>
                        </div><!--/Main-->
                    </div><!--/Row-->
                </div><!--/Content fluid-->
            </div><!--/Page content wrapper-->
        </div><!--/Wrapper-->

        <!-- Modal to change email an admin -->
        <div class="modal fade" id="emailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel">Email an Admin</h4>
                        <p>Please describe your problem in as much detail as possible.</p>
                    </div>
                    <form method="post" action="EmailAdmin">
                        <div class="modal-body">
                            <div class="form-group">
                                <input class="form-control" type="text" 
                                       name="subject" id="subject" placeholder="Enter Subject Here..."/><br>
                                <textarea class="form-control" id="body" name="body"
                                          rows="4" cols="20" placeholder="Enter Email here..."></textarea>
                            </div>
                            <%
                                Member member = (Member) session.getAttribute("member");
                            %>
                            <div class="modal-footer">
                                <input type="text" name="memberID" value="<%=member.getId()%>" hidden/>
                                <input type="hidden" name="whichContact" value="member" />
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-default">Send Email</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div><!--/Modal to email admin-->
        <!-- Include all JavaScript -->
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
