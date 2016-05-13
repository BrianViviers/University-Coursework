<!--Header navbar when logging in or registering. Reduced functionality - return to home only.-->
<%@ page import="applicationconfig.AppServletContextListener" %>
<%
    AppServletContextListener context = new AppServletContextListener();
%>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"/>
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<%=context.getWebAppURLInsecure()%>index.jsp">Plymouth LETS</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="<%=context.getWebAppURLInsecure()%>index.jsp">Home</a></li>
            </ul>
        </div>
    </div>
</nav>