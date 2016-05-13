<!-- A navigation bar for advert creation - reduced functionality -->
<nav class="navbar navbar-inverse navbar-fixed-top">
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
        <div class="col-sm-1">
            <button type="button" class="btn btn-default btn-xs" onclick="startAgain()">
                <img src="img/startAgainx24.png" alt="Advanced Mode"/><br>Start Advert Again <!--Redirect to first advert creation page-->
            </button>
        </div>
        <script type="text/javascript">
            // Redirect member to create advert type page, starting advert creation again. Discards all data.
            function startAgain(){ 
                var url = "createAdvertType.jsp"; window.location.replace(url); 
            }
        </script>
        <div id="navbar" class="navbar-collapse collapse"><!--Controls to return home or logout-->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="MemberHome">Home</a></li>
                <li>
                    <form class="navbar-form" action="Logout">
                        <button type="submit" class="btn btn-xs btn-default"><img src="img/logoutx24.png"/> LOGOUT</button>
                    </form> 
                </li>
            </ul>
        </div>
    </div>
</nav>