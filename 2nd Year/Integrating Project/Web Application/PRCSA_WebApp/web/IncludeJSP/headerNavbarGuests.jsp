<!--Header navbar for guests - reduced functionality. No reference to account, bids or transactions-->
<%@ page import="applicationconfig.AppServletContextListener" %>
<%
    AppServletContextListener context = new AppServletContextListener();
%>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">Plymouth LETS</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="index.jsp">Home</a></li>
                <li><a href="BrowseAdverts?no_user=1">Browse Adverts</a></li>
                <li><a href="GuestRules">Rules</a></li>
                <li><a href="guestHelp.jsp">Help</a></li>
                <li>
                    <a href="<%=context.getWebAppURL()%>login.jsp">
                        <button type="button" class="btn btn-xs btn-default"><img src="img/loginx24.png" />LOGIN</button>
                    </a>
                </li>
                <li><a href="<%=context.getWebAppURL()%>register.jsp">
                        <button type="button" class="btn btn-xs btn-default"><img src="img/registerx24.png" />Register</button>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>