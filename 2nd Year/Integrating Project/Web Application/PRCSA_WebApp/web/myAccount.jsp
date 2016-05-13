<%-- 
    Document   : myAccount
    Created on : 18-Feb-2015, 22:21:56
    Author     : BrianV
--%>
<!--A member's account page. Controls to change any of their details are here.-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entities.Member" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>

<%
    Member member = (Member) session.getAttribute("member");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date d = sdf.parse(member.getDob().toString());
    sdf.applyPattern("dd MMM yyyy");
    String newDob = sdf.format(d);
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="../favicon.ico">

        <title>My Account - LETS Plymouth</title>
        <c:import url="css.jsp"></c:import>
        </head>
        <body>
            <div id="wrapper">
            <c:import url="IncludeJSP/headerNavbarMembers.jsp"></c:import>
            <c:import url="IncludeJSP/leftNavbarMembers.jsp">
                <c:param name="active" value="account"/>
            </c:import>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <button value="<<" class="btn btn-xs btn-default pull-left" id="menu-toggle">
                            <img id="side-toggle" src="img/side-left.png" alt="toggle sidebar"/>
                        </button>
                        <div class="main">
                            <h1 class="page-header">Account Details</h1>
                            <div class="col-sm-8 col-md-6"><!--Member details-->
                                <h3>Hi <%= member.getForename()%></h3><br>
                                <h5>Need to change your details? This is the place.</h5><br>
                                <h3>My Info</h3>
                                <table class="table table-striped">
                                    <tbody>
                                        <tr><td>Full Name: </td>
                                            <td><span class="text-info"><%=member.getForename()%> <%=member.getSurname()%></span></td></tr>
                                        <tr><td colspan="100%"><a href='#' class="text-info" onclick="HideMessage();" data-toggle="modal" data-target="#nameModal">Change My Name</a></td></tr>
                                        <tr><td>Email Address: </td>
                                            <td><span class="text-info"><%=member.getEmail()%></span></td></tr>
                                        <tr><td colspan="100%"><a href="#" class="text-info" onclick="HideMessage();" data-toggle="modal" data-target="#emailModal">Change My Email Address</a></td></tr>
                                        <tr><td>Date of Birth: </td>
                                            <td><span class="text-info"><%=newDob%></span></td></tr>
                                        <tr><td colspan="100%"><a href="#" class="text-info" onclick="HideMessage();" data-toggle="modal" data-target="#dobModal">Change My Date of Birth</a></td></tr>
                                        <tr><td>Contact Number: </td>
                                            <td><span class="text-info"><%=member.getContact_number()%></span></td></tr>
                                        <tr><td colspan="100%"><a href="#" class="text-info" onclick="HideMessage();" data-toggle="modal" data-target="#contactModal">Change My Contact No.</a></td></tr>
                                        <tr><td>My Address: </td>
                                            <td><span class="text-info"><%=member.getAddline1()%><br>
                                                        <%
                                                        if (member.getAddline2() != null) {
                                                        %>                                                        
                                                    <%=member.getAddline2()%><br>
                                                    <%
                                                        }
                                                    %>
                                                    <%=member.getCity()%> <br>
                                                    <%=member.getPostcode()%></span></td></tr>
                                        <tr><td colspan="100%"><a href="#" class="text-info" onclick="HideMessage();" data-toggle="modal" data-target="#addressModal">Change My Address</a></td></tr>
                                        <tr><td colspan="100%">Need to change your password?</td></tr>
                                        <tr><td colspan="100%"><a href="#" class="text-info" onclick="HideMessage();" data-toggle="modal" data-target="#passwordModal">Change My Password</a></td></tr>
                                    </tbody>
                                </table>
                            </div><!--/Member details-->
                            <div class="col-sm-4 col-md-4">
                                <%
                                    String message = (String) session.getAttribute("return_message");
                                    session.removeAttribute("return_message");
                                    if (message != null) {
                                        if (message.startsWith("There was a problem") || message.startsWith("Incorrect")
                                                || message.startsWith("Email address already")) {
                                %>
                                <div class="alert alert-warning" id="submit_msg" role="alert">
                                    <%= message.toString()%>
                                </div> 
                                <%
                                } else {
                                %>
                                <div class="alert alert-success" id="submit_msg" role="alert">
                                    <%= message.toString()%>
                                </div> 
                                <%
                                        }
                                    }
                                %>
                            </div>

                            <!-- Modal to change name -->
                            <div class="modal fade" id="nameModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">Update Your Name</h4>
                                        </div>
                                        <form method="post" action="UpdateMemberDetails">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">First Name</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="text" class="form-control text-capitalize" value="<%=member.getForename().toString()%>" id="forename" name="forename" required/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">Surname</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="text" class="form-control text-capitalize" value="<%=member.getSurname().toString()%>" id="surname" name="surname" required/>
                                                    </div>
                                                </div>                                           
                                                <input type="text" name="memberID" value="<%=member.getId().toString()%>" hidden/>
                                                <input type="text" name="whichDetails" value="changeName" hidden/>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-default">Save changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <!-- Modal to change email -->
                            <div class="modal fade" id="emailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">Update Your Email Address</h4>
                                        </div>
                                        <form method="post" action="UpdateMemberDetails" onsubmit="return CheckEmail(this);">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">New Email Address</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="email" class="form-control text-lowercase" id="email" name="email" required/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">Re-Type New Email</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="email" onchange="compareEmail()" class="form-control text-lowercase" id="reenteremail" name="retype_email" required/>
                                                        <div class="alert alert-warning hide" id="incorrect_email" role="alert">Email's don't match</div>
                                                    </div>
                                                </div>
                                                <input type="text" name="memberID" value="<%=member.getId().toString()%>" hidden/>
                                                <input type="text" name="whichDetails" value="changeEmail" hidden/>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-default">Save changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <!-- Modal to change password -->
                            <div class="modal fade" id="passwordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">Update Your Password</h4>
                                        </div>
                                        <form method="post" action="UpdateMemberDetails" onsubmit="return CheckPassword(this);">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">Current Password</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="password" class="form-control" id="old_password" name="old_password" required/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">New Password</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="password" class="form-control" id="password" name="new_password" required/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">Re-Type New Password</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="password" onkeyup="comparePass()" class="form-control" id="reenterpassword" name="new_password_retype" required/>
                                                        <div class="alert alert-warning hide" id="incorrect_pass" role="alert">Passwords don't match</div>
                                                    </div>
                                                </div>
                                                <input type="text" name="memberID" value="<%=member.getId().toString()%>" hidden/>
                                                <input type="text" name="email" value="<%=member.getEmail().toString()%>" hidden/>
                                                <input type="text" name="whichDetails" value="changePassword" hidden/>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-default">Save changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <!-- Modal to change Date of Birth -->
                            <div class="modal fade" id="dobModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">Update Your Date of Birth</h4>
                                        </div>
                                        <form method="post" action="UpdateMemberDetails">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 register-label">Date of Birth (dd/mm/yyyy)</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="text" id="datepicker" name="dob" required>
                                                    </div>
                                                </div>                                  
                                                <input type="text" name="memberID" value="<%=member.getId().toString()%>" hidden/>
                                                <input type="text" name="whichDetails" value="changeDOB" hidden/>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-default">Save changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <!-- Modal to change address -->
                            <div class="modal fade" id="addressModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">Update Your Address</h4>
                                        </div>
                                        <form method="post" action="UpdateMemberDetails">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">Address Line 1</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="text" class="form-control" value="<%= member.getAddline1()%>" name="addline1" required/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">Address Line 2</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="text" class="form-control" value="<%= member.getAddline2()%>" name="addline2" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">City</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="text" class="form-control" value="<%= member.getCity()%>" name="city" required/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">Post Code</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="text" class="form-control text-uppercase" value="<%= member.getPostcode()%>" name="postcode" required/>
                                                    </div>
                                                </div>
                                                <input type="text" name="memberID" value="<%=member.getId().toString()%>" hidden/>
                                                <input type="text" name="whichDetails" value="changeAddress" hidden/>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-default">Save changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <!-- Modal to change contact no. -->
                            <div class="modal fade" id="contactModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">Update Your Contact No.</h4>
                                        </div>
                                        <form method="post" action="UpdateMemberDetails" onsubmit="return CheckContactNo(this);">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">Contact Number</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="text" class="form-control" id="contactNo" value="<%= member.getContact_number()%>" name="contactNo" required/>
                                                        &nbsp;<span id="errmsg1"></span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-xs-5 text-info">Re-Type Contact No.</label>
                                                    <div class="input-group col-xs-5">
                                                        <input type="text" class="form-control" id="retype_contactNo" name="retype_contactNo" required/>
                                                        &nbsp;<span id="errmsg2"></span>
                                                    </div>
                                                </div>
                                                <input type="text" name="memberID" value="<%=member.getId().toString()%>" hidden/>
                                                <input type="text" name="whichDetails" value="changeContact" hidden/>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-default">Save changes</button>
                                            </div>
                                        </form>
                                    </div><!--/Modal content-->
                                </div><!--/Modal dialog-->
                            </div><!--/Modal-->
                        </div><!--/Main-->
                    </div><!--/Row-->
                </div><!--/Container fluid-->
            </div><!--/Page content wrapper-->
        </div><!--/Wrapper-->
    <c:import url="javascript.jsp"></c:import>
    <script type='text/javascript'>

        // Hiding messages when selecting edit any details
        function HideMessage() {
            document.getElementById("submit_msg").style.display = "none";
        }
        
        // Validating email changes
        function compareEmail() {
            if (document.getElementById('email').value === document.getElementById('reenteremail').value) {
                document.getElementById("incorrect_email").style.display = "none";
            } else {
                document.getElementById("incorrect_email").style.display = "inline";
            }
        }

        // Validating password changes
        function comparePass() {
            if (document.getElementById('password').value === document.getElementById('reenterpassword').value) {
                document.getElementById("incorrect_pass").style.display = "none";
            } else {
                document.getElementById("incorrect_pass").style.display = "inline";
            }
        }

        // Validating email changes
        function CheckEmail(form) {
            if (form.email.value !== form.reenteremail.value) {
                alert('Those email addresses don\'t match!');
                return false;
            } else {
                return true;
            }
        }

        // Checking passwords match and their length. Must be >= 5
        function CheckPassword(form) {
            if (form.password.value !== form.reenterpassword.value) {
                alert('Those passwords don\'t match!');
                return false;
            } else {
                if (document.getElementById("password").value.length < 5) {
                    alert('Password is too short!');
                    return false;
                } else {
                    return true;
                }
            }
        }

        // Validating contact number
        function CheckContactNo(form) {
            if (form.contactNo.value !== form.retype_contactNo.value) {
                alert('Those phone numbers don\'t match!');
                return false;
            } else {
                return true;
            }
        }

        // Ensuring contact number inputs can only receive numbers
        $(document).ready(function () {
            $("#contactNo").keypress(function (e) {
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    $("#errmsg1").html("Digits Only").show().fadeOut("slow");
                    return false;
                }
            });
            
            $("#retype_contactNo").keypress(function (e) {
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    $("#errmsg2").html("Digits Only").show().fadeOut("slow");
                    return false;
                }
            });
        });
    </script>
    <script src="js/pwstrength.js"></script>
    <script type="text/javascript">
        // Enabling password checking/rating controls
        jQuery(document).ready(function () {
            "use strict";
            var options = {
                minChar: 8,
                bootstrap3: true,
                errorMessages: {
                    password_too_short: "<font color='red'>The Password is too short</font>",
                    same_as_username: "Your password cannot be the same as your username"
                },
                scores: [17, 26, 40, 50],
                verdicts: ["Weak", "Normal", "Medium", "Strong", "Very Strong"],
                showVerdicts: true,
                showVerdictsInitially: false,
                raisePower: 1.4,
                usernameField: "#email"
            };
            $('#password').pwstrength(options);
            document.getElementById("progressBar").style.backgroundColor = "#FFF";
        });
    </script>
</body>
</html>
