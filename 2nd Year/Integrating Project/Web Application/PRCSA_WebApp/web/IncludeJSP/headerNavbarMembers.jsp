<!--Member header navbar. Full functionality - includes links to all services. -->
<%@page import="entities.Member" %>
<%@page import="java.lang.NullPointerException" %>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="MemberHome">Plymouth LETS</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="createAdvertType.jsp">
                        <input type="button" class="btn btn-xs btn-default" value="Post an ad">
                    </a>
                </li>
                <li><a href="MemberHome">Home</a></li>
                <li><a href="BrowseAdverts">Browse Adverts</a></li>
                <li role="presentation" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-expanded="false">
                        <i class="glyphicon glyphicon-user"></i><span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="myAccount.jsp">My Account</a></li>
                        <li><a href="MyAdverts">My Adverts</a></li>
                        <li><a href="MyTransactions">My Transactions</a></li>
                        <li><a href="MyBids">My Bids</a></li>
                        <li><a href="SearchMembers">Find Members</a></li>
                        <li><a href="help.jsp">Help</a></li>
                        <li><a href="Rules">Rules</a></li>
                    </ul>
                </li>
                <li>&nbsp;&nbsp;&nbsp;</li><!--Welcoming the member by name-->
                    <%
                        Member member = (Member) session.getAttribute("member");
                        try {
                    %>
                <li style="color: white; font-size: 14pt; margin-top: 10px"><small>Hello</small> <b><%= member.getForename()%></b></li>
                <li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
                <li style="color: white; font-size: 14pt; margin-top: 10px"><%= member.getBalance()%>&nbsp;<i class="fa fa-money fa-lg"></i></li>
                    <%
                        } catch (NullPointerException ex) {
                            request.getRequestDispatcher("serverError.jsp").forward(request, response);
                        }
                    %>
                <li>
                    <form class="navbar-form" action="Logout">
                        <button type="submit" class="btn btn-xs btn-default"><img src="img/logoutx24.png"/> LOGOUT</button>
                    </form> 
                </li>
            </ul>
        </div>
    </div>
</div>

