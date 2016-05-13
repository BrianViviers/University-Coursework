<!-- Bootstrap core JavaScript
        ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="js/vendor/jquery.min.js"></script>
<script src="js/vendor/bootstrap.min.js"></script>
<script src="js/bootstrap-select.min.js"></script>
<script src="js/jquery.bootstrap-autohidingnavbar.js"></script>
<script>
    // JQuery to handle hiding navbars
    $('.dropdown').hover(function () {
        $('.dropdown-toggle', this).trigger('click');
    });
    
    $("div.navbar-fixed-top").autoHidingNavbar();
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
        if ($("#menu-toggle").val() === "<<") {
            $("#menu-toggle").val(">>");
            $("#side-toggle").attr("src","img/side-right.png");
        }
        else {
            $("#menu-toggle").val("<<");
            $("#side-toggle").attr("src","img/side-left.png");
        }
    });
    
    $('#popoverData').popover();
    $('#popoverData').popover({ trigger: "hover" });
</script>