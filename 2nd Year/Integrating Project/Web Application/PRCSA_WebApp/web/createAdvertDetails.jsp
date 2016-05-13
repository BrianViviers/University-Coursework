<%-- 
    Document   : createAdvertDetails
    Created on : 18-Feb-2015, 22:27:19
    Author     : DominicY
--%>
<!--A page, as part of the default advert creation process, to input the transport, title, description, credits and add an image.-->
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
        <link rel="icon" href="../favicon.ico">

        <title>LETS Plymouth</title>

        <c:import url="css.jsp"></c:import>
        </head>

        <body onload='onload();'>
        <c:import url="IncludeJSP/headerCreateAdverts.jsp"></c:import>
            <div class="container-fluid">
                <div class="text-center">
                    <h1 class="page-header text-center">Enter your Advert Details</h1>
                </div>
                <form action="DisplayAdvert"  method="post" onsubmit="return checkDetails(this);"><!--Hidden form to store advert details for upload-->
                    <div class="row">
                        <div class="col-sm-6"><!--Left half of page-->
                            <div class=" placeholders">
                                <div class="col-xs-4">
                                    <h4 id="parAdvertType"><b>Advert Type</b><br></h4>
                                    <input type="text" id="advertType" name="advertType" hidden/>
                                </div>
                                <div class="col-xs-4">
                                    <h4 id="parItemType"><b>Item Type</b><br></h4>
                                    <input type="text" id="itemType" name="itemType" hidden/>
                                </div>
                                <div class="col-xs-4">
                                    <h4 id="parCategory"><b>Category</b><br></h4>
                                    <input type="text" id="category" name="category" hidden/>
                                </div>
                            </div>
                            <div class="text-center"><!--Transport Selection-->
                                <br> <h3 class="text-primary text-center" id="parTransport"></h3>
                                <div class="btn-group" data-toggle="buttons">
                                    <label id="lblOffer" class="btn btn-group btn-default">
                                        <input type="radio" id="btnTransporttrue" onchange="handleTransport(true)">
                                        <img id="imgTransporttrue" src="img/yesx24.png" alt="Yes" onclick="handleTransport(true)"/><br>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    </label>
                                    <label class="btn btn-group btn-default">
                                        <input type="radio" id="btnTransportfalse" onchange="handleTransport(false)">
                                        <img id="imgTransportfalse" src="img/nox24.png" alt="No" onclick="handleTransport(false)"/><br>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    </label>
                                </div>
                            </div>
                            <div class="row placeholders" id="divTitle" hidden><!--Inputting title - hidden until transport is selected-->
                                <div class="col-sm-10 col-sm-offset-1">
                                    <div class="form-group">
                                        <h3 class="text-primary text-center">Please enter your advert title</h3>
                                        <input class="form-control" type="text" onkeypress="titleChanged()"
                                               name="textTitle" id="textTitle" placeholder="Enter Title Here..."/>
                                    </div>
                                </div>
                            </div>
                            <div class="row placeholders" id="divDescription" hidden><!--Inputting description - hidden until title contains text-->
                                <div class="col-sm-10 col-sm-offset-1">
                                    <div class="form-group">
                                        <h3 class="text-primary text-center">Please enter your advert description</h3>
                                        <textarea class="form-control" id="textDescription" name="textDescription" onkeypress="descriptionChanged()"
                                                  rows="4" cols="20" placeholder="Enter Description here..."></textarea>
                                        <label>Remember to specify which days of the week you are free!</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row placeholders" id="divCredits" hidden><!--Inputting credits - hidden until description contains text-->
                                <div class="col-sm-10 col-sm-offset-1">
                                    <div class="form-group">
                                        <h3 class="text-primary text-center">Please enter the amount of credits</h3>
                                        <input class="form-control" type="text" id="textCredits" onkeypress="creditsChanged()"
                                               name="textCredits" placeholder="Please enter the number of credits..."/>
                                        &nbsp;<span id="errmsg"></span>
                                    </div>
                                </div>
                            </div>
                        </div><!--/Left half of page-->
                        <div class="col-sm-6" id="divImage" hidden><!--Right half of page - image display and upload controls-->
                            <div class="jumbotron" style="float: right;">
                                <h2 class="text-primary text-center">Your Advert Image</h2>
                                <p class="text-center" id="parImageNotify">This is a default image. Please add your own with the controls to the left.</p>
                                <div class="col-xs-3">
                                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#imageModal">
                                        <img src="img/addImagex24.png" alt="Add Image"/><br>Manage Image</button>
                                </div>
                                <div class='col-xs-6 col-xs-offset-1'><!--Thumbnail display-->
                                    <span id ="spanImg"></span>
                                    <output id="list"></output>
                                </div>
                            </div>
                            <div class="form-group col-sm-offset-4 col-xs-offset-4"><br>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="submit" style="width:200px;height:60px;font-size:16pt" class="btn btn-default" value="Preview Advert">
                            </div>
                        </div><!-- /Right half of page - image display and upload controls-->
                    </div><!--/Row-->
                    <div class="row"><!--Hidden fields for advert upload-->
                        <div class="col-xs-offset-5">
                            <input type="text" name="transport" id="transport" hidden/>
                            <input type="text" name="image" id="image" hidden/>
                        </div>
                    </div>
                    <!-- Modal to add an image -->
                    <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title text-center">Add an Image</h4>
                                </div>
                                <div class="modal-content">
                                    <input class="btn btn-lg" type="file" id="files"/>
                                    <div class="col-xs-offset-3">
                                        <span id="modalImg"></span>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">
                                        <img src="img/yesx24.png" alt="Confirm"/><br>Confirm Image Selection</button>
                                    <button class="btn btn-default" type="button" id="btnRemoveImage">
                                        <img src="img/removeImagex24.png" alt="Remove"/><br>Revert to Default Image</button>
                                </div>
                            </div>
                        </div>
                    </div><!--/Image Modal-->
                </form>
                <hr>
                <footer>
                    <p>&copy; Plymouth LETS 2015</p>
                </footer>
            </div><!--/Container-->

        <c:import url="javascript.jsp"></c:import>
        <script>
            $("#mode-group .btn").on('click', function () {
                $("#period-group").button('reset');
            });

            $('.selectpicker').selectpicker();

        </script>


        <script type='text/javascript'>
            var query;
            var transport = null;

            // Showing advert type, item type and category from URL query
            function onload() {
                query = getQueryParams(document.location.search);
                document.getElementById("parCategory").innerHTML += query.Category;
                document.getElementById("category").value = query.Category;
                document.getElementById("parAdvertType").innerHTML += query.Type;
                document.getElementById("advertType").value = query.Type;
                document.getElementById("parItemType").innerHTML += query.Item;
                document.getElementById("itemType").value = query.Item;
                document.getElementById("parTransport").innerHTML = "Is Transport included in your advert?";
                insertDefaultImage(query.Category);//Select and display a default image from item category
            }

            // Inserting and displaying a default image from the item query
            function insertDefaultImage(category)
            {
                var span = document.getElementById("spanImg");
                var arg;

                switch (category) {
                    case "Accounting":
                        arg = "img/accountingx256.png";
                        break;
                    case "Cleaning":
                        arg = "img/cleaningx256.png";
                        break;
                    case "Computing and Electronics":
                        arg = "img/electronicsx256.png";
                        break;
                    case "Education":
                        arg = "img/educationx256.png";
                        break;
                    case "Health":
                        arg = "img/healthx256.png";
                        break;
                    case "Home and Garden":
                        arg = "img/homeGardenx256.png";
                        break;
                    case "Maintenance":
                        arg = "img/maintenancex256.png";
                        break;
                    case "Printing":
                        arg = "img/printingx256.png";
                        break;
                    case "Recreation":
                        arg = "img/recreationx256.png";
                        break;
                    case "Transport":
                        arg = "img/transportx256.png";
                        break;
                    case "Other":
                        arg = "img/otherx256.png";
                        break;
                }
                span.innerHTML = "<img class='thumb' src='" + arg + "'/>";
                document.getElementById('image').value = arg;
            }

            // Accessing the URL quuery and storing in an object
            function getQueryParams(qs) {
                qs = qs.split("+").join(" ");

                var params = {}, tokens,
                        re = /[?&]?([^=]+)=([^&]*)/g;
                while (tokens = re.exec(qs)) {
                    params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
                }
                return params;
            }

            // Storing transport as a boolean and showing title input field
            function handleTransport(boolTransport)
            {
                transport = boolTransport;
                if (boolTransport) {
                    document.getElementById("transport").value = "true";
                } else {
                    document.getElementById("transport").value = "false";
                }
                document.getElementById("divTitle").style.display = "inline";
            }

            // When the title is changed, show the description input field
            function titleChanged() {
                document.getElementById("divDescription").style.display = "inline";
            }

            // When the description is changed, show the credits input field
            function descriptionChanged() {
                document.getElementById("divCredits").style.display = "inline";
            }

            // When credits are changed, show the image controls.
            function creditsChanged() {
                document.getElementById("divImage").style.display = "inline";
            }

            // Input validation - ensuring all fields contain data for advert upload
            function checkDetails(form) {
                if (transport === null)
                {
                    alert("Please indicate if transport is included.");
                    return false;
                }
                else if (!form.textTitle.value.length > 0)
                {
                    alert("Please enter a title for your advert.");
                    return false;
                }
                else if (form.textCredits.value.length === 0)
                {
                    alert("Please enter a number for the amount of credits.");
                    return false;
                }
                else if (!form.textDescription.value.length > 0)
                {
                    alert("Please enter a description for your advert.");
                    return false;
                }
                else
                {
                    if (!form.image.value.length > 0)  // Ensure the user is aware a default image will be uploaded if they do not upload one
                    {
                        if (confirm("Are you sure you don't want to add your own image?\n\
                \n\nClick cancel to add an image or OK to continue to preview.")) {
                            return true;
                        }
                        return false;
                    } else {
                        return true;
                    }
                }
            }

            var control = $("#files");

            // JQuery handling image upload removal, again inserting a default image from the category
            $("#btnRemoveImage").on("click", function () {
                control.replaceWith(control = control.clone(true));
                document.getElementById('files').addEventListener('change', handleFileSelect, true);
                document.getElementById("files").disabled = false;
                insertDefaultImage(query.Category);
            });

            // Ensuring only numbers can be entered in credit field
            $(document).ready(function () {
                $("#textCredits").keypress(function (e) {
                    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                        $("#errmsg").html("Digits Only").show().fadeOut("slow");
                        return false;
                    }
                });
            });

            //File selection script
            function handleFileSelect(evt) {
                var files = evt.target.files; // FileList object
                // Loop through the FileList and render image files as thumbnails.
                for (var i = 0, f; f = files[i]; i++) {
                    // Only process image files.
                    if (!f.type.match('image.*')) {
                        continue;
                    }
                    var reader = new FileReader();
                    // Closure to capture the file information.
                    reader.onload = (function (theFile) {
                        return function (e) {
                            // Render thumbnail.
                            //var span = document.createElement('span');
                            var span = document.getElementById("spanImg");
                            span.innerHTML = ['<img class="thumb" src="', e.target.result,
                                '" title="', escape(theFile.name), '"/>'].join('');
                            document.getElementById('image').value = e.target.result;
                            document.getElementById('list').insertBefore(span, null);
                            // Render the thumbnail in the modal, too
                            document.getElementById("modalImg").innerHTML = ['<img id="modalThumb" class="thumb" src="', e.target.result,
                                '" title="', escape(theFile.name), '"/>'].join('');
                        };
                    })(f);
                    // Read in the image file as a data URL.
                    reader.readAsDataURL(f);
                }
                cleanPageAfterImageAdd();
            }

            // Removing "This is a default image" text when an image is uploaded.
            function cleanPageAfterImageAdd()
            {
                document.getElementById("parImageNotify").innerHTML = "";
            }

            document.getElementById('files').addEventListener('change', handleFileSelect, false);
        </script>
    </body>
</html>
