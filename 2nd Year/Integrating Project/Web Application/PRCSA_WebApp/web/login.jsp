<%-- 
    Document   : login
    Created on : 18-Feb-2015, 22:03:22
    Author     : BrianV
--%>
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

        <title>LETS Plymouth: Login</title>

        <c:import url="css.jsp"></c:import>

        </head>
        <body>
        <c:import url="IncludeJSP/headerLoginRegister.jsp"></c:import>

        <div class="container-fluid">
            <div class="row">
                <div class="main">
                    <div class="container"><!--Sign in controls-->
                        <form class="form-signin" method="post" action="VerifyLogin">
                            <h2 class="form-signin-heading">Please sign in</h2>
                            <label for="inputEmail" class="sr-only">Email address</label>
                            <input type="email" id="inputEmail" class="form-control" placeholder="Email address" name="email" required autofocus>
                            <label for="inputPassword" class="sr-only">Password</label>
                            <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="password" required>
                            <button class="btn btn-lg btn-default btn-block" type="submit">Sign in</button>
                        </form>
                        <%                            String message = (String) request.getAttribute("message");
                            if (message != null) {
                        %>
                        <div class="alert alert-success form-signin" id="incorrect_pass" role="alert">
                            <%= message.toString()%>
                        </div> 
                        <%
                            }
                        %>
                    </div>
                </div><!--/Main-->
            </div><!--/Row-->
        </div><!--/Container-->
        <c:import url="javascript.jsp"></c:import>
    </body>
</html>
