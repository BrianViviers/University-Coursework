<!--Left member navbar. Full functionality, includes links to all pages and services.-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%!
    String home, account, rules, help, myAdverts, myTransactions, myBids, browseAds, findMembers = "";
    String home2, account2, rules2, help2, myAdverts2, myTransactions2, myBids2, browseAds2, findMembers2 = "";
%>
<!--Handling currently viewed page - colouring selected navbar item.-->
<%
    if (request.getParameter("active").equals("home")) {
        home = "active";
        home2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("account")) {
        account = "active";
        account2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("rules")) {
        rules = "active";
        rules2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("help")) {
        help = "active";
        help2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("myAdverts")) {
        myAdverts = "active";
        myAdverts2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("myTransactions")) {
        myTransactions = "active";
        myTransactions2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("myBids")) {
        myBids = "active";
        myBids2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("browseAds")) {
        browseAds = "active";
        browseAds2 = "<span class=\"sr-only\">(current)</span>";
    } else if (request.getParameter("active").equals("findMembers")) {
        findMembers = "active";
        findMembers2 = "<span class=\"sr-only\">(current)</span>";
    }
%>

<div id="sidebar-wrapper" class="move-below-header">
    <ul class="nav nav-sidebar">
        <li><c:import url="IncludeJSP/searchBarMembers.jsp"></c:import></li>
        
        <li class="<%= home%>"><a href="/PRCSA_WebApp/MemberHome">
                <img src="img/homex24.png" alt="Home"/>&nbsp;Home
                <c:if test="${not empty home2}"><%= home2%></c:if>

                </a>
            </li>
            <li class="<%= account%>"><a href="myAccount.jsp">
                <img src="img/accountx24.png" alt="Account"/>&nbsp;Account
                <c:if test="${not empty account2}"><%= account2%></c:if>
                </a>
            </li>
            <li class="<%= rules%>"><a href="Rules">
                <img src="img/rulesx24.png" alt="Rules"/>&nbsp;Rules
                <c:if test="${not empty rules2}"><%= rules2%></c:if>
                </a>
            </li>
            <li class="<%= help%>"><a href="help.jsp">
                <img src="img/questionx24.png" alt="Help"/>&nbsp;Help
                <c:if test="${not empty help2}"><%= help2%></c:if>
                </a>
            </li>
        </ul>
        <ul class="nav nav-sidebar">
            <li class="<%= myAdverts%>"><a href="MyAdverts">
                <img src="img/myAdvertsx24.png" alt="My Adverts"/>&nbsp;My Adverts
                <c:if test="${not empty myAdverts2}"><%= myAdverts2%></c:if>
                </a>
            </li>
            <li class="<%= myTransactions%>"><a href="MyTransactions">
                <img src="img/myTransactionsx24.png" alt="My Transactions"/>&nbsp;My Transactions
                <c:if test="${not empty myTransactions2}"><%= myTransactions2%></c:if>
                </a>
            </li>
            <li class="<%= myBids%>"><a href="MyBids">
                <img src="img/myBidsx24.png" alt="My Bids"/>&nbsp;My Bids
                <c:if test="${not empty myBids2}"><%= myBids2%></c:if>
                </a>
            </li>
            <li class="<%= browseAds%>"><a href="BrowseAdverts">
                <img src="img/browsex24.png" alt="Browse Adverts"/>&nbsp;Browse Adverts
                <c:if test="${not empty browseAds2}"><%= browseAds2%></c:if>
                </a>
            </li>
            <li class="<%= findMembers%>"><a href="SearchMembers">
                <i class="fa fa-users fa-lg" style="color:black"></i>&nbsp;&nbsp;Find Members
                <c:if test="${not empty findMembers2}"><%= findMembers2%></c:if>
                </a>
            </li>
            <li><br><br></li>
        </ul>
        <a href="createAdvertType.jsp">
            <input type="button" class="btn btn-default" value="Post an ad">
        </a>
    </div><!--/Sidebar Wrapper-->
<%
    home = account = rules = help = myAdverts = myTransactions = myBids = browseAds = findMembers = "";
    home2 = account2 = rules2 = help2 = myAdverts2 = myTransactions2 = myBids2 = browseAds2 = findMembers2 = "";
%>