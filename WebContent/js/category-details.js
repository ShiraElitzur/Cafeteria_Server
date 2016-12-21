var icons = [];
var items = [];
var mealsToDel = [];
var firstItem = true;
var meals = new Array();
var icon_index = 0;
var firstMeal = true;
var categoryEdit;
var localIcons = [
		iconAddress + "/icons/hamburger.png",
		iconAddress + "/icons/wrap.png",
		iconAddress + "/icons/turkey.png",
		iconAddress + "/icons/kebab.png",
		iconAddress + "/icons/tea.png",
		iconAddress + "/icons/water.png",
		iconAddress + "/icons/can.png",
		iconAddress + "/icons/candy1.png",
		iconAddress + "/icons/candy.png",
		iconAddress + "/icons/cupcake.png",
		iconAddress + "/icons/croissant.png",
		iconAddress + "/icons/ice-cream.png",
		iconAddress + "/icons/gingerbread.png",
		iconAddress + "/icons/ice-cream1.png",
		iconAddress + "/icons/salad1.png",
		iconAddress + "/icons/pizza.png",
		iconAddress + "/icons/fish.png",
		iconAddress + "/icons/pie.png",
		iconAddress + "/icons/risotto.png",
		iconAddress + "/icons/fries.png",
		iconAddress + "/icons/pasta.png",
		iconAddress + "/icons/spaguetti.png",
		iconAddress + "/icons/sandwich.png",
		iconAddress + "/icons/pot.png",
		iconAddress + "/icons/chef.png",
		iconAddress + "/icons/mixer1.png",
		iconAddress + "/icons/pan.png"
	];

$(document)
    .ready(
        function () {

            meals = [];
            // if we returned from add meal
            if (sessionStorage.getItem("meal") !== null) {
                var temp = JSON.parse(sessionStorage.getItem("meal"));
                sessionStorage.removeItem("meal");
                // if this is the first meal
                if (sessionStorage.getItem("meals") === null) {
                    sessionStorage.setItem("meals", meals);
                } else {
                    // if we have more meals
                    meals = JSON.parse(sessionStorage.getItem("meals"));
                }
                // add the new meal
                meals.push(temp);
                // save the meals
                sessionStorage.setItem("meals", JSON.stringify(meals));
            }

            // if there wasn't a meal in storage
            if (sessionStorage.getItem("inEdit") !== null) {
                meals = JSON.parse(sessionStorage.getItem("meals"));
            }

            // if we get out from this page remove all
            // session storage variables
            $("#backToCategories").click(function () {
                sessionStorage.removeItem("meal");
                sessionStorage.removeItem("meals");
                sessionStorage.removeItem("inEdit");
                sessionStorage.removeItem("saveDetails");
                window.location.href = "./home.html";
            });

            // before moving to meal details page, save title
            // and description. to-do: add icon.
            $("#addMealBtn").click(function () {
                var title = $("#categoryTitle").val();
                sessionStorage.setItem("categoryTitle", title);
                var desc = $("#categoryDescription").val();
                sessionStorage.setItem("categoryDescription", desc);
                var icon = $('#theIcon').attr("src");
                sessionStorage.setItem("categoryIcon", icon);
                sessionStorage.setItem("inEdit", true);
                if (sessionStorage.getItem("editMeal") !== null && sessionStorage.getItem("editMeal") !== undefined){
                    sessionStorage.setItem("categoryExist", true);
                }
                if (items.length > 0){
                	sessionStorage.setItem("items",JSON.stringify(items));
                }
                window.location = "./meal-details.html";
            });

            // check if all fileds are filled
            $("#addItem").click(function () {
                var itemTitle = $("#itemTitle").val();
                var itemPrice = $("#itemPrice").val();
                if (itemPrice.length < 1 || itemTitle.length < 1) {
                    alert("אנא מלא את כל השדות");
                } else {
                    addItem(itemTitle, itemPrice);
                    $("#itemTitle").val("");
                    $("#itemPrice").val("");
                }
            });

            $("#categoryDetailsForm").submit(function () {
                $("#saveCategory").attr("disabled", true);
                if ($("#saveCategory").text() === "עדכן") {
                    updateCategoryinDB();
                } else {
                    saveCategoryinDB();
                }
                sessionStorage.removeItem("meals");
                return false;
            });

            // if we are in edit category
            if (sessionStorage.getItem("categoryEdit") !== null && sessionStorage.getItem("categoryEdit") !== undefined){
                categoryEdit = JSON.parse(sessionStorage
                    .getItem("categoryEdit"));
                sessionStorage.removeItem("categoryEdit");
                sessionStorage.setItem("inEdit", true);

                $("#categoryTitle").val(categoryEdit.title);
                $("#categoryDescription").val(categoryEdit.description);
                items = categoryEdit.items;
                if (items.length > 0) {
                    $
                        .each(
                            items,
                            function (index, element) {
                                $('#itemsTable')
                                    .append(
                                        $('<tr id="' + element.title + '"><td>' + element.title + '</td>' + '<td>' + element.price + '</td>' + '<td> <button type="button" class="btn my-button" id="editItem"> <span id="editText">ערוך</span> <span' + ' class="glyphicon glyphicon-pencil"></span></button></td> <td><button type="button" class="btn my-button" id="deleteItem"' + '>הסר<span class="glyphicon glyphicon-remove"></span></button></td></tr>'));
                            })
                    if (firstItem === true) {
                        addOnClickFunctions();
                        firstItem = false;
                    }
                }
                meals = categoryEdit.meals;
                sessionStorage.setItem("meals", JSON.stringify(meals));

                $("#saveCategory").text("עדכן");
                $('#pageTitle').text("עריכת קטגוריה");

               
                var temp = arrayBufferToBase64(categoryEdit.icon);
                var imgSrc = "data:image/png;base64," + temp;
                $('#theIcon').attr("src", imgSrc);
                sessionStorage.setItem("categoryIcon", imgSrc);
                sessionStorage.setItem("categoryId", categoryEdit.id);

            }

            // to cover a case that we was in edit category,
            // and then moved to meal details
            if (sessionStorage.getItem("inEdit") !== null && sessionStorage.getItem("inEdit") !== undefined) {
                $("#saveCategory").text("עדכן");
                $('#pageTitle').text("עריכת קטגוריה");
            }
            
           if (sessionStorage.getItem("categoryExist") !== null && sessionStorage.getItem("categoryExist") !== undefined){
               $("#saveCategory").text("שלח");
               $('#pageTitle').text("הוספת קטגוריה");
           }

            if (meals !== null && meals.length > 0) {
                initMealsTable();
            }

            if (sessionStorage.getItem("saveDetails") !== null && sessionStorage.getItem("saveDetails") !== undefined) {
                $("#categoryTitle").val(
                    sessionStorage.getItem("categoryTitle"));
                $("#categoryDescription").val(
                    sessionStorage.getItem("categoryDescription"));
                var icon = sessionStorage.getItem("categoryIcon");
                $('#theIcon').attr("src", icon);
                $("#saveCategory").text("שלח");
                $('#pageTitle').text("הוספת קטגוריה");
                items = JSON.parse(sessionStorage.getItem("items"));
                if (items.length > 0) {
                    $
                        .each(
                            items,
                            function (index, element) {
                                $('#itemsTable')
                                    .append(
                                        $('<tr id="' + element.title + '"><td>' + element.title + '</td>' + '<td>' + element.price + '</td>' + '<td> <button type="button" class="btn my-button" id="editItem"> <span id="editText">ערוך</span> <span' + ' class="glyphicon glyphicon-pencil"></span></button></td> <td><button type="button" class="btn my-button" id="deleteItem"' + '>הסר<span class="glyphicon glyphicon-remove"></span></button></td></tr>'));
                            })
                    if (firstItem === true) {
                        addOnClickFunctions();
                        firstItem = false;
                    }
                }
                
            }
            sessionStorage.setItem("saveDetails", true);

        });

function initIcons() {
    var editIndex;
    icons = [];
    $.each(localIcons, function (index, element) {

        icons.push({
            id: element.id,
            icon: element.icon
        });
        $('.image-picker').append(
            "<option data-img-src= " + element + " value=" + index + "> </option>");

        if (sessionStorage.getItem("inEdit") === undefined) {
            if (categoryEdit !== undefined) {
                if (element.icon === categoryEdit.icon) {
                    editIndex = index;
                }
            }
        }

    })

    $("select").imagepicker({
        show_label: false,
        selected: function () {
            icon_index = this.val();
            $('#theIcon').attr("src", localIcons[icon_index]);
        }
    });

    if (sessionStorage.getItem("inEdit") === null) {
        var firstIcon = localIcons[0];
        $('#theIcon').attr("src", firstIcon);

    } else {
        $("select").val(-1);
        $("select").data('picker').sync_picker_with_select();
    }

}

function addItem(title, price) {
    items.push({
        id: 0,
        title: title,
        price: price
    });

    $('#itemsTable')
        .append(
            $('<tr id="' + title + '"><td>' + title + '</td>' + '<td>' + price + '</td>' + '<td> <button type="button" class="btn my-button" id="editItem"> <span id="editText">ערוך</span> <span' + ' class="glyphicon glyphicon-pencil"></span></button></td> <td><button type="button" class="btn my-button" id="deleteItem"' + '>הסר<span class="glyphicon glyphicon-remove"></span></button></td></tr>'));

    if (firstItem === true) {
        addOnClickFunctions();
        firstItem = false;
    }

}

function addOnClickFunctions() {
    $(document).on("click", "#itemsTable #deleteItem", function (e) {
        var row = $(this).closest('tr');
        var index = row.index();
        var tableRowId = "#" + items[index].title;
        $(tableRowId).remove();
        items.splice(index, 1);
    });

    $(document)
        .on(
            "click",
            "#itemsTable #editItem",
            function (e) {

                var row = $(this).closest('tr');
                var index = row.index();
                var titleCell = $(this).closest('tr').find('td:first');
                var priceCell = $(this).closest('tr').find('td:eq(1)');

                if ($(this).closest('tr').find("#editText").text() === ("ערוך")) {
                    titleCell.prop('contenteditable', true);
                    priceCell.prop('contenteditable', true);
                    titleCell.attr('contenteditable', true);
                    titleCell.css("border", "0.5px solid blue");
                    priceCell.css("border", "0.5px solid blue");
                    $(this).closest('tr').find("#editText").text('עדכן');

                } else if ($(this).closest('tr').find("#editText")
                    .text() === ("עדכן")) {
                    $(this).closest('tr').find("#editText").text("ערוך");
                    titleCell.prop('contenteditable', false);
                    priceCell.prop('contenteditable', false);
                    titleCell.attr('contenteditable', false);
                    titleCell.css("border", "none");
                    priceCell.css("border", "none");
                    editItem(index, titleCell, priceCell);
                }

            });
}

function editItem(index, titleCell, priceCell) {
    items[index].title = titleCell.text();
    items[index].price = priceCell.text();
}

function initMealsTable() {

    $
        .each(
            meals,
            function (index, element) {
                $('#mealsTable')
                    .append(
                        $('<tr id="' + element.id + '"><td>' + element.title + '</td>' + '<td> <button type="button" class="btn my-button" id="editMeal">ערוך<span' + ' class="glyphicon glyphicon-pencil"></span></button></td> <td><button type="button" class="btn my-button" ' + 'id="deleteMeal">הסר<span class="glyphicon glyphicon-remove"></span></button></td></tr>'));
            })

    if (firstMeal === true) {
        addMealOptions();
        firstMeal = false;
    }
}

function addMealOptions() {

    $(document).on("click", "#mealsTable #deleteMeal", function (e) {
        var row = $(this).closest('tr');
        var index = row.index();
        var tableRowId = "#" + meals[index].id;
        $(tableRowId).remove();
        mealsToDel.push(meals[index]);
        meals.splice(index, 1);

    });

    $(document).on("click", "#mealsTable #editMeal", function (e) {
        var row = $(this).closest('tr');
        var index = row.index();
        var mealEdit = meals[index];
        sessionStorage.setItem("editMealIndex", index);
        sessionStorage.setItem("editMeal", JSON.stringify(mealEdit));
        var title = $("#categoryTitle").val();
        sessionStorage.setItem("categoryTitle", title);
        var desc = $("#categoryDescription").val();
        sessionStorage.setItem("categoryDescription", desc);
        window.location = "./meal-details.html";
    });
}

function arrayBufferToBase64(buffer) {
    var binary = '';
    var bytes = new Uint8Array(buffer);
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
}

function base64ToArrayBuffer(base64) {
    var binary_string = window.atob(base64);
    var len = binary_string.length;
    var bytes = new Uint8Array(len);
    for (var i = 0; i < len; i++) {
        bytes[i] = binary_string.charCodeAt(i);
    }
    return bytes.buffer;
}

function saveCategoryinDB() {
    var urlAddress = server + "/rest/web/addCategory";

    var categoryTitle = $("#categoryTitle").val();
    var categoryDesc = $("#categoryDescription").val();
    var icon = localIcons[icon_index];

    $.post(urlAddress, {
        id: 0,
        title: categoryTitle,
        description: categoryDesc,
        items: JSON.stringify(items),
        meals: JSON.stringify(meals),
        icon: icon
    }, function (data, status) {
        // alert("Data: " + data + "\nStatus: " + status);
        window.location = "./home.html";

        if (data === null) {
            alert("null");
        } else {

        }
    });

}

function updateCategoryinDB() {
    var urlAddress = server + "/rest/web/updateCategory";

    var categoryTitle = $("#categoryTitle").val();
    var categoryDesc = $("#categoryDescription").val();
    var icon = localIcons[icon_index];
    var id = sessionStorage.getItem("categoryId");

    var category = {
        id: id,
        title: categoryTitle,
        description: categoryDesc,
        items: JSON.stringify(items),
        meals: JSON.stringify(meals),
        icon: icon
    };

    // alert("category id: " + category.id + "\n" +
    // "category title: " + category.title + "\n" +
    // "category description: " + category.description + "\n" +
    // "category items: " + items + "\n" +
    // "cateory meals: " + category.meals + "\n" +
    // "cateory icon: " + icon + "\n"
    //
    // );

    $.post(urlAddress, {
        id: id,
        title: categoryTitle,
        description: categoryDesc,
        items: JSON.stringify(items),
        meals: JSON.stringify(meals),
        icon: icon
    }, function (data, status) {
        // alert("Data: " + data + "\nStatus: " + status);
        if (mealsToDel !== null) {
            deleteMeals(mealsToDel);
        }
        window.location = "./home.html";

        if (data === null) {
            alert("null");
        } else {

        }
    });

}

function deleteMeals(mealsToDel) {
    var urlAddress = server + "/rest/web/deleteMeals";

    $.ajax({
        type: "POST",
        url: urlAddress,
        data: JSON.stringify(mealsToDel),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });

}

//function HandleBackFunctionality() {
//    if (window.event) // IE
//    {
//        if (window.event.clientX < 40 && window.event.clientY < 0) {
//            alert("Browser back button is clicked...");
//        } else {
//            alert("Browser refresh button is clicked...");
//        }
//    } else // Chrome
//    {
//        if (event.currentTarget.performance.navigation.type == 1) // firefox
//        {
//            alert("Browser refresh button is clicked...");
//        }
//        if (event.currentTarget.performance.navigation.type == 2) // firefox
//        {
//            alert("Browser back button is clicked...");
//        }
//    }
//}