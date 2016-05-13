<!--Guest left navbar. Reduced functionality - home, browse adverts, rules, help-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%!
    String home, rules, adverts, help = "";
    String home2, rules2, adverts2, help2 = "";
%>
<!--Handling active navbar component - colouring the currently selected item.-->
<%
    if (request.getParameter("active").equals("home")) {
        home = "active";
        home2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("adverts")) {
        adverts = "active";
        adverts2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("rules")) {
        rules = "active";
        rules2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("help")) {
        help = "active";
        help2 = "<span class=\"sr-only\">(current)</span>";
    }

%>

<div id="sidebar-wrapper" class="move-below-header">
    <ul class="nav nav-sidebar">
        <li><c:import url="IncludeJSP/searchBarGuests.jsp"></c:import></li>
        <li class="<%= home%>"><a href="index.jsp">
                <img src="img/homex24.png" alt="Home"/>&nbsp;Home
                <c:if test="${not empty home2}"><%= home2%></c:if>
                </a>
            </li>
            <li class="<%= adverts%>"><a href="BrowseAdverts?no_user=1">
                <img src="img/browsex24.png" alt="Browse"/>&nbsp;Browse Adverts
                <c:if test="${not empty adverts2}"><%= adverts2%></c:if>
                </a>
            </li>
            <li class="<%= rules%>"><a href="GuestRules">
                <img src="img/rulesx24.png" alt="Rules"/>&nbsp;Rules
                <c:if test="${not empty rules2}"><%= rules2%></c:if>
                </a>
            </li>
            <li class="<%= help%>"><a href="guestHelp.jsp">
                <img src="img/questionx24.png" alt="Help"/>&nbsp;Help
                <c:if test="${not empty help2}"><%= help2%></c:if>
                </a>
            </li>
        </ul>
    </div>
<%
    home = rules = adverts = help = "";
    home2 = rules2 = adverts2 = help2 = "";
%>