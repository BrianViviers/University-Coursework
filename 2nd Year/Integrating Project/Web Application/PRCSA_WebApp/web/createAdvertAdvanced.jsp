<%-- 
    Document   : createAdvertAdvanced
    Created on : 18-Feb-2015, 22:29:26
    Author     : DominicY
--%>
<!--Advanced advert creation - the entire advert can be built on one page. This page can also be accessed when "Edit my advert is 
    selected from the Preview Advert page.-->
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

        <body onload="onLoad()">
        <c:import url="IncludeJSP/headerCreateAdverts.jsp"></c:import>

            <div class="container-fluid"><!--Page Container-->
                <form action="DisplayAdvert" name="pageSubmit" method="post" onsubmit="return checkDetails(this);"><!--Advert Details Form-->
                    <div class="row">
                        <div class="text-center">
                            <h1 class="page-header">Create an Advert - Advanced</h1>
                        </div>
                        <div class="col-sm-5">
                            <div class="row"><!--Advert Type Selection-->
                                <div id="advertGroup" class="btn-group col-md-offset-3 col-sm-offset-3 col-xs-offset-2" data-toggle="buttons">
                                    <h4>Which type of Advert?</h4>
                                    <label id="lblOffer" class="btn btn-group btn-primary"><!--Advert Type = Offer-->
                                        <input type="radio" id="btnOffer" checked=""> 
                                        <img src="img/offerx128.png" id="imgOffer" alt="Offer" class="img_size_advert_advanced" onclick="handleAdvertType('Offer')"/><br>
                                        Offer
                                    </label>
                                    <label class="btn btn-group btn-primary"><!--Advert Type = Request-->
                                        <input type="radio" id="btnRequest"> 
                                        <img src="img/requestx128.png" id="imgRequest" alt="Request" class="img_size_advert_advanced"  onclick="handleAdvertType('Request')"/><br>
                                        Request
                                    </label>
                                    <input type="text" id="advertType" name="advertType" hidden/>
                                </div>
                            </div><!--/Advert Type Selection-->
                            <br>
                            <div class="row">
                                <div id="divItemType" class="" hidden><!--Item Type Selection - Hidden until Ad Type is selected-->
                                    <div id="itemGroup" class="btn-group col-md-offset-3 col-sm-offset-3 col-xs-offset-2" data-toggle="buttons">
                                        <h4>Which type of Item?</h4>
                                        <label class="btn btn-group btn-primary"><!--Item type = Product-->
                                            <input type="radio" id="btnProduct"> 
                                            <img src="img/productx128.png" id="imgProduct" alt="Product" class="img_size_advert_advanced" onclick="handleItemType('Product')" /><br>
                                            Product
                                        </label>
                                        <label class="btn btn-group btn-primary"><!--Item type = Service-->
                                            <input type="radio" id="btnService"> 
                                            <img src="img/servicex128.png" id="imgService" alt="Service" class="img_size_advert_advanced" onclick="handleItemType('Service')" /><br>
                                            Service
                                        </label>
                                        <input type="text" id="itemType" name="itemType" hidden/>
                                    </div>
                                </div>
                            </div>
                            <br>

                            <div id="divTransport" class="row" hidden><!--Transport indication - Hidden until item type is selected-->
                                <div class="btn-group col-md-offset-3 col-sm-offset-3 col-xs-offset-2" data-toggle="buttons">
                                    <h4>Is Transport Included in your Advert?</h4>
                                    <label id="lblOffer" class="btn btn-group btn-primary"><!--Transport is included-->
                                        <input type="radio" id="btnTransporttrue" onchange="handleTransport(true)">
                                        <img id="imgTransporttrue" src="img/yesx24.png" alt="Yes" onclick="handleTransport(true)"/><br>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    </label>
                                    <label class="btn btn-group btn-primary"><!--Transport is not included-->
                                        <input type="radio" id="btnTransportfalse" onchange="handleTransport(false)">
                                        <img id="imgTransportfalse" src="img/nox24.png" alt="No" onclick="handleTransport(false)"/><br>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div id="divCategory" class="row" hidden><!--Item category selection - Hidden until transport is resolved-->
                                <div class="text-center col-sm-8 col-md-6">
                                    <select name="selectCategory" id="selectCategory" class="selectpicker" data-width="100%" onchange="handleCategory()"><!--Item category-->
                                        <option>Please select a Category</option>
                                        <option>Accounting</option>
                                        <option>Cleaning</option>
                                        <option>Computing and Electronics</option>
                                        <option>Education</option>
                                        <option>Health</option>
                                        <option>Home and Garden</option>
                                        <option>Maintenance</option>
                                        <option>Printing</option>
                                        <option>Recreation</option>
                                        <option>Transport</option>
                                        <option>Other</option>
                                    </select>
                                </div>
                                <input type="text" id="category" name="category" hidden/>
                            </div>
                            <br>
                            <div id="divRestOfPage" hidden><!--Advert details - title, description and credits. Hidden until category is selected-->
                                <div id="divTitle" class="row placeholders" onchange="handleTitle()">
                                    <div class="text-center col-sm-10 col-md-8"><!--Advert title input field-->
                                        <div class="form-group">
                                            <label>Please enter your advert title</label>
                                            <input class="form-control" type="text" name="textTitle" id="textTitle" placeholder="Enter Title Here..."/>
                                        </div>
                                    </div>
                                </div>

                                <div id="divDescription" class="row placeholders" onchange="handleDescription()">
                                    <div class="text-center col-sm-10 col-md-8"><!--Advert description input field-->
                                        <div class="form-group">
                                            <label>Please enter your advert description (2000 character limit)<br>
                                                Remember to indicate which days of the week you are available!</label>
                                            <textarea class="form-control" id="textDescription" name="textDescription" rows="4" cols="20" placeholder="Enter Description here..."></textarea>
                                        </div>
                                    </div>
                                </div>

                                <div id="divCredits" class="row placeholders" onchange="handleCredits()">
                                    <div class="text-center col-sm-10 col-md-8"><!--Advert credits input field-->
                                        <div class="form-group">
                                            <label>Please enter the amount of credits</label>
                                            <input class="form-control" type="text" id="textCredits" onkeypress="showImageDiv()" name="textCredits" placeholder="Please enter the number of credits..."/>
                                            &nbsp;<span id="errmsg"></span>
                                        </div>
                                    </div>
                                </div>

                                <div id="divImage" class="row" style="float: left;" hidden>
                                    <h2 class="text-primary text-center">Your Advert Image</h2><!--Image upload - hidden until title, description and credits inputs contain data-->
                                    <p class="text-center" id="parImageNotify">This is a default image. Please add your own with the controls to the left.</p>
                                    <div class="col-xs-3">
                                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#imageModal">
                                            <img src="img/addImagex24.png" alt="Add Image"/><br>Manage Image</button><!--Opens image modal-->
                                    </div>
                                    <div class='col-xs-6 col-xs-offset-3'><!--Thumbnail display-->
                                        <span id ="spanImg">
                                            <img id="imgMain" class="thumb"/>
                                        </span>
                                        <output id="list"></output>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-xs-offset-5"><!--Hidden form inputs for transport and image-->
                                    <input type="text" name="transport" id="transport" hidden/>
                                    <input type="text" name="image" id="image" hidden/>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div id="divPreviewButton" class="row col-xs-12" hidden>
                        <div class="form-group text-center"><!--Preview image button - hidden until all fields contain data-->
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="submit" style="width:200px;height:60px;font-size:16pt" class="btn btn-default" value="Preview Advert">
                        </div>
                    </div>
                </form><!--/Advert Details Form-->

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
                                    <span id="modalImg">
                                    </span>
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
                </div><!--/Modal for image-->
            </div>

        <c:import url="javascript.jsp"></c:import>
            <script>//JQuery function to enable button groups
                $("#mode-group .btn").on('click', function () {
                    $("#period-group").button('reset');
                });

                $('.selectpicker').selectpicker();

            </script>

            <script type='text/javascript'>

                var query;
                var adType;
                var itemType;
                var category;
                var categorySRC;
                var credits;
                var title;
                var description;
                var transport;

                function onLoad()
                {
                    query = getQueryParams(document.location.search);
                    // Setting up the page when returning to edit the advert.
                    if (query.Category) // If a URL query exists, the advert is being edited. Fill inputs and select button groups from query.
                    {
                        document.getElementById("divItemType").style.display = "inline"; //Show all dividers on advert edit
                        document.getElementById("divCategory").style.display = "inline";
                        document.getElementById("divRestOfPage").style.display = "inline";
                        document.getElementById("divTransport").style.display = "inline";
                        document.getElementById("img" + query.Type).click();
                        document.getElementById("img" + query.Item).click();
                        document.getElementById("imgTransport" + query.Transport).click();
                        document.getElementById("textTitle").value = query.Title;
                        document.getElementById("textDescription").value = query.Description;
                        document.getElementById("textCredits").value = query.Credits;
                        selectCategoryFromQuery();
                        showImageDiv();
                    }
                }

                // Accessing the URL query and storing details in an object.
                function getQueryParams(qs) {
                    qs = qs.split("+").join(" ");

                    var params = {}, tokens,
                            re = /[?&]?([^=]+)=([^&]*)/g;

                    while (tokens = re.exec(qs)) {
                        params[decodeURIComponent(tokens[1])]
                                = decodeURIComponent(tokens[2]);
                    }

                    return params;
                }

                // Selecting the correct category from the URL query when returning to edit an advert
                function selectCategoryFromQuery()
                {
                    var categoryIndex;
                    var selectCategory = document.getElementById("selectCategory");

                    for (var i = 0; i < selectCategory.options.length; i++)
                    {

                        if (selectCategory.options[i].value === query.Category)
                        {
                            categoryIndex = i;
                            break;
                        }
                    }
                    selectCategory.selectedIndex = categoryIndex;
                    $('.selectpicker').selectpicker('refresh');
                    handleCategory();
                }

                // Storing the advert type as a string
                function handleAdvertType(advertType)
                {
                    document.getElementById("divItemType").style.display = "inline"; // Allow item type selection
                    adType = advertType;
                    document.getElementById("advertType").value = adType;
                }

                // Storing the item type as a string
                function handleItemType(iType)
                {
                    document.getElementById("divTransport").style.display = "inline"; // Allow transport indication 
                    itemType = iType;
                    document.getElementById("itemType").value = itemType;
                }

                // Storing the transport type as a boolean
                function handleTransport(boolTransport)
                {
                    document.getElementById("divCategory").style.display = "inline"; // Allow item category selection
                    transport = boolTransport;
                    if (boolTransport) {
                        document.getElementById("transport").value = "true";
                    } else {
                        document.getElementById("transport").value = "false";
                    }
                }
            <%
                request.getParameter("image"); //Image handling
            %>

                // Storing the item category as a string
                function handleCategory()
                {
                    document.getElementById("divRestOfPage").style.display = "inline"; // Show title, description and credits inputs
                    var catList = document.getElementById("selectCategory");
                    category = catList.options[catList.selectedIndex].text;
                    document.getElementById("category").value = category;
                    insertDefaultImage(category);
                }

                // Store the credit value as an integer
                function handleCredits()
                {
                    var creditsBox = credits = document.getElementById("textCredits");
                    credits = creditsBox.value;
                    creditsBox.style.border = "";
                    if (isNaN(credits) || credits.length === 0 || credits <= 0) // Validation - credits must be a number above 0
                    {
                        document.getElementById("textCredits").style.border = "1px solid red";
                        alert("Please enter a valid number for the amount of credits.");
                    }
                }

                // Storing the advert title as a string
                function handleTitle()
                {
                    var titleBox = document.getElementById("textTitle");
                    title = document.getElementById("textTitle").value;
                    titleBox.style.border = "";
                }
                
                // Handling default image insert - this is done by default and is selected by the item category.
                function insertDefaultImage(category)
                {
                    var span = document.getElementById("spanImg");
                    switch (category) {
                        case "Accounting":
                            categorySRC = "img/accountingx256.png";
                            break;
                        case "Cleaning":
                            categorySRC = "img/cleaningx256.png";
                            break;
                        case "Computing and Electronics":
                            categorySRC = "img/electronicsx256.png";
                            break;
                        case "Education":
                            categorySRC = "img/educationx256.png";
                            break;
                        case "Health":
                            categorySRC = "img/healthx256.png";
                            break;
                        case "Home and Garden":
                            categorySRC = "img/homeGardenx256.png";
                            break;
                        case "Maintenance":
                            categorySRC = "img/maintenancex256.png";
                            break;
                        case "Printing":
                            categorySRC = "img/printingx256.png";
                            break;
                        case "Recreation":
                            categorySRC = "img/recreationx256.png";
                            break;
                        case "Transport":
                            categorySRC = "img/transportx256.png";
                            break;
                        case "Other":
                            categorySRC = "img/otherx256.png";
                            break;
                    }
                    document.getElementById('image').value = categorySRC;
                    document.getElementById('imgMain').src = categorySRC;
                }

                // Storing the advert description as a string
                function handleDescription()
                {
                    var descriptionBox = document.getElementById("textDescription");
                    description = descriptionBox.value;
                    descriptionBox.style.border = "";
                }

                // Showing the image controls and the preview button - only when all fields are complete 
                function showImageDiv() {
                    document.getElementById("divPreviewButton").style.display = "inline";
                    document.getElementById("divImage").style.display = "inline";
                }

                // Validation function when preview button is clicked - checking all fields are complete and showing error messages.
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
                        if (!form.image.value.length > 0)
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

                // JQuery handling the removal of an image and reverting to the default category image
                $("#btnRemoveImage").on("click", function () {
                    control.replaceWith(control = control.clone(true));
                    document.getElementById('files').addEventListener('change', handleFileSelect, true);
                    document.getElementById("files").disabled = false;
                    insertDefaultImage(category);
                });

                // JQuery blocking non-numeric input in the credits box
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
                                var img = document.getElementById("imgMain");
                                img.src = e.target.result;
                                document.getElementById('image').value = e.target.result;
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
                
                // Hiding the "This is a default image" text when an image is uploaded.
                function cleanPageAfterImageAdd()
                {
                    document.getElementById("parImageNotify").innerHTML = "";
                }

                document.getElementById('files').addEventListener('change', handleFileSelect, false);
        </script>
    </body>
</html>
