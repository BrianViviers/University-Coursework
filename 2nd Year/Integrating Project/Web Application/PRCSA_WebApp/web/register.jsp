<%-- 
    Document   : guestRules
    Created on : 18-Feb-2015, 22:03:22
    Author     : BrianV
--%>
<!--A page to register for the LETS system-->
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

        <title>LETS Plymouth: Registration</title>

        <link rel="stylesheet" href="css/jquery-ui.css">
        <script src="js/jquery-1.10.2.js"></script>

        <script src="js/jquery-ui.js"></script>
        <script>
            // JQuery enabling datepicker
            $(function () {
                $("#datepicker").datepicker({
                    format: 'dd/mm/yyyy'
                });
            });
        </script>

        <!-- Include all CSS -->
        <c:import url="css.jsp"></c:import>
            <link href="css/datepicker.css" rel="stylesheet">

        </head>

        <body onload="onload()">
        <c:import url="IncludeJSP/headerLoginRegister.jsp"></c:import>

            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-8 col-sm-offset-2 main">
                        <div class="register-div">
                            <form method="post" action="Register" onsubmit="return checkEmail(this);" ><!--Form for registration details-->
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">First Name*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="text" class="form-control text-capitalize" name="firstname" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Surname*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="text" class="form-control text-capitalize" name="surname" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Date of Birth* (dd/mm/yyyy)</label>
                                    <div class="input-group col-xs-5">
                                        <input type="text" id="datepicker" name="dob" required>
                                    </div>
                                </div><br><br>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Email Address*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="email" class="form-control text-lowercase" id="email" name="email" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Confirm Email*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="text" onchange="compareEmail()" class="form-control text-lowercase" id="reenteremail" name="renteremail" required/>
                                        <div class="alert alert-warning" id="incorrect_email" role="alert">Email's don't match</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Password*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="password" id='password' class="form-control" name="password" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Confirm Password*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="password" onkeyup="comparePass()" id='reenterpassword' class="form-control" name="reenterpassword" required/>
                                        <div class="alert alert-warning" id="incorrect_pass" role="alert">Passwords don't match</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Telephone No.*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="text" class="form-control" id="telephone" name="telephone" required/>
                                        &nbsp;<span id="errmsg"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Address Line 1*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="text" class="form-control" name="addline1" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Address Line 2</label>
                                    <div class="input-group col-xs-5">
                                        <input type="text" class="form-control" name="addline2"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">City*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="text" class="form-control text-capitalize" name="city" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-5 register-label">Post Code*</label>
                                    <div class="input-group col-xs-5">
                                        <input type="text" class="form-control text-uppercase" name="postcode" required/>
                                    </div>
                                </div>
                                <div class="form-group col-xs-3 register-label pull-right">
                                    <div class="input-group col-xs-5">
                                        <input class="btn btn btn-default" type="submit" value="Register"/>
                                    </div>
                                </div><br>
                            <%
                                String message = (String) request.getAttribute("message");
                                if (message != null) {
                            %>
                            <div class="alert alert-warning form-group col-xs-5 col-md-offset-5" role="alert">
                                <%
                                    out.println(message);
                                %>
                            </div> 
                            <%
                                }
                            %>
                            </form>
                        </div><!--/Register-->
                    </div><!--/Main-->
                </div><!--/Row-->
            </div><!--/Container-fluid-->



        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Include all JavaScript -->
        <c:import url="javascript.jsp"></c:import>
        <script src="js/bootstrap-datepicker.min.js"></script>
        <script src="js/pwstrength.js"></script>


        <script type="text/javascript">
            
            // Hiding incorrect email or password notifications
            function onload() {
                document.getElementById("incorrect_email").style.display = "none";
                document.getElementById("incorrect_pass").style.display = "none";
            }
            //Validating email
            function compareEmail() {
                if (document.getElementById('email').value === document.getElementById('reenteremail').value) {
                    document.getElementById("incorrect_email").style.display = "none";
                } else {
                    document.getElementById("incorrect_email").style.display = "inline";
                }
            }
            
            // Validating email
            function comparePass() {
                if (document.getElementById('password').value === document.getElementById('reenterpassword').value) {
                    document.getElementById("incorrect_pass").style.display = "none";
                } else {
                    document.getElementById("incorrect_pass").style.display = "inline";
                }
            }

            // Ensuring email and passwords match. Ensuring password is length >= 5
            function checkEmail(form) {
                if (form.email.value !== form.reenteremail.value) {
                    alert('Those email addresses don\'t match!');
                    return false;
                } else {
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
            }
            //Only allowing numeric input to telephone number input.
            $(document).ready(function () {
                $("#telephone").keypress(function (e) {
                    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                        $("#errmsg").html("Digits Only").show().fadeOut("slow");
                        return false;
                    }
                });
            });
        </script> 
        <script type="text/javascript">

            // JQuery enabling password strength indicator
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
            });
        </script>
    </body>
</html>
