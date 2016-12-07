var dualllistbox;
var servings = [];
var mains = [];
var extras = [];
var selectedIndex;
var selectedServingIndex;
var newMain = true;
var newServing = true;
var inEdit = false;
var editMeal;
$(document).ready(function () {
    $("#existing-main").hide();
    $("#new-main").hide();
    
    $("#existing-serving").hide();
    $("#new-serving").hide();

    $("#back").click(function () {
        sessionStorage.removeItem("editMeal");
        sessionStorage.removeItem("editMealIndex");
        window.location.href = "./category-details.html";
    });
    
    $('#selectpickerMains').selectpicker();
    initMains();
    
    $('#selectpickerServings').selectpicker();
    initServings();

    dualllistbox = $('select[name="duallistbox"]').bootstrapDualListbox({
        nonSelectedListLabel: 'Non-selected',
        selectedListLabel: 'Selected',
        preserveSelectionOnMove: 'moved',

    });


    $("#addExtra").click(function () {
        var title = $('#extraTitle').val();
        var price = $('#extraPrice').val();
        if (title.length < 1 || price.length < 1) {
            alert("you must fill all fields");
        } else {
            $('#extraTitle').val("");
            $('#extraPrice').val("");
            addExtra(title, price);
        }
    });

    $("#addExistingMain").click(function () {
        $("#new-main").hide();
        $("#existing-main").show();
    });

    $("#addNewMain").click(function () {
        $("#new-main").show();
        $("#existing-main").hide();
    });
    
    $("#addExistingServing").click(function () {
        $("#new-serving").hide();
        $("#existing-serving").show();
    });
    
    $("#addNewserving").click(function () {
        $("#new-serving").show();
        $("#existing-serving").hide();
    });

    $("#mealDetailsForm").submit(function () {
        if ($('#new-main').is(":hidden") && $('#existing-main').is(":hidden")) {
            alert("Fill main !");
            return false;
        }
        if ($('#new-main').is(":visible")) {
            if ($('#mainTitle').val().length < 1) {
                alert("main is empty");
                return false;
            }
        } else {
            if (selectedIndex === undefined) {
                alert("main is empty");
                return false;
            }
        }
        
        if ($('#new-serving').is(":hidden") && $('#existing-serving').is(":hidden")) {
            alert("Fill serving form !");
            return false;
        }
        if ($('#new-serving').is(":visible")) {
            if ($('#servingTitle').val().length < 1) {
                alert("serving is empty");
                return false;
            }
        } else {
            if (selectedServingIndex === undefined) {
                alert("serving is empty");
                return false;
            }
        }

        $("#saveMeal").attr("disabled", true);

        var mealTitle = $('#mealTitle').val();
        var mealPrice = $('#mealPrice').val();

        var extraAmount = $('#extraAmount').val();

        var selectedExtras = [];
        var extrasIndexes = $('[name="duallistbox"]').val();
        $.each(extrasIndexes, function (index, element) {
            var thisExtra = findExtra(element);

            selectedExtras.push({
                id: thisExtra.id,
                title: thisExtra.title,
                price: thisExtra.price
            });
        })
        var id;
        if (editMeal !== undefined) {
            id = editMeal.id;
        } else {
            id = 0;
        }

        var meal = {
            id: id,
            main: "to be filled",
            title: mealTitle,
            extras: selectedExtras,
            extraAmount: extraAmount,
            price: mealPrice,
            serving: "to be filled",
            includesDrink: $('#includesDrink').prop('checked')
        };

        if ($('#new-main').is(":visible")) {

            addNewMainToDB($('#mainTitle').val(),
                function (result) {
                    meal.main = result;
                    if ($("#saveMeal").text() == "Update") {
                        var meals = JSON.parse(sessionStorage.getItem("meals"));
                        var index = sessionStorage.getItem("editMealIndex");
                        meals[index] = meal;
                        sessionStorage.setItem("meals", JSON.stringify(meals));
                        sessionStorage.removeItem("editMeal");
                        sessionStorage.removeItem("editMealIndex");
                    } else {
                        sessionStorage.setItem("meal", JSON.stringify(meal));
                    }


                });

        } else if ($('#existing-main').is(":visible")) {
            meal.main = mains[selectedIndex - 1];
            newMain = false;
        }
        
        if ($('#new-serving').is(":visible")) {
            addNewServingToDB($('#servingTitle').val(),
                function (result) {
                    meal.serving = result;
                    if ($("#saveMeal").text() == "Update") {
                        var meals = JSON.parse(sessionStorage.getItem("meals"));
                        var index = sessionStorage.getItem("editMealIndex");
                        meals[index] = meal;
                        sessionStorage.setItem("meals", JSON.stringify(meals));
                        sessionStorage.removeItem("editMeal");
                        sessionStorage.removeItem("editMealIndex");
                    } else {
                        sessionStorage.setItem("meal", JSON.stringify(meal));
                    }

                });

        } else if ($('#existing-serving').is(":visible")) {
            meal.serving = servings[selectedServingIndex - 1];
            newServing = false;
        }

        //        alert("meal title: " + meal.title + "\n" +
        //            "meal price: " + meal.price + "\n" +
        //            "main id: " + meal.main.id + "\n" +
        //            "main title: " + meal.main.title + "\n" +
        //            "extra amount: " + meal.extraAmount + "\n" +
        //            "selected extra: " + meal.extras.length
        //        );

        if (newMain === true) {

        } else {
            if ($("#saveMeal").text() == "Update") {
                var meals = JSON.parse(sessionStorage.getItem("meals"));
                var index = sessionStorage.getItem("editMealIndex");
                meals[index] = meal;
                sessionStorage.setItem("meals", JSON.stringify(meals));
                sessionStorage.removeItem("editMeal");
                sessionStorage.removeItem("editMealIndex");
                window.location.href = "./category-details.html";
            } else {
                sessionStorage.setItem("meal", JSON.stringify(meal));
            }

        }
        
        if (newServing === true) {
            window.location.href = "./category-details.html";

        } else {
            if ($("#saveMeal").text() == "Update") {
                var meals = JSON.parse(sessionStorage.getItem("meals"));
                var index = sessionStorage.getItem("editMealIndex");
                meals[index] = meal;
                sessionStorage.setItem("meals", JSON.stringify(meals));
                sessionStorage.removeItem("editMeal");
                sessionStorage.removeItem("editMealIndex");
                window.location.href = "./category-details.html";
            } else {
                sessionStorage.setItem("meal", JSON.stringify(meal));
            }
            window.location.href = "./category-details.html";

        }

        return false;


    });

    $('#selectpickerMains').on('changed.bs.select', function (event, clickedIndex, newValue, oldValue) {
        selectedIndex = clickedIndex;
    });
    
    $('#selectpickerServings').on('changed.bs.select', function (event, clickedIndex, newValue, oldValue) {
        selectedServingIndex = clickedIndex;
    });

    if (sessionStorage.getItem("editMeal") !== null) {
        inEdit = true;
        editMeal = JSON.parse(sessionStorage.getItem("editMeal"));

        $('#mealTitle').val(editMeal.title);
        $('#mealPrice').val(editMeal.price);
        var main = editMeal.main;
        $("#existing-main").show();
        $("#existing-serving").show();

        $('#extraAmount').val(editMeal.extraAmount);
        $('#saveMeal').text("Update");
        $('#pageTitle').text("Edit Meal");
        if (editMeal.includesDrink === true){
        $('#includesDrink').prop('checked', true);
        } else{
            $('#includesDrink').prop('checked', false);
        }
    }

});

function initExtras() {
    var editExtras;
    if (inEdit === true) {
        editMeal = JSON.parse(sessionStorage.getItem("editMeal"));
        editExtras = editMeal.extras;
    }
    var theUrl = server + "/rest/web/getExtras";
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: theUrl,
        timeout: 5000,
        success: function (data, textStatus) {
            //            alert('request successful');
            $.each(data, function (index, element) {
                var found = false;
                extras.push({
                    id: element.id,
                    title: element.title,
                    price: element.price
                });
                if (inEdit === false) {
                    dualllistbox.append('<option value="' + element.id + '">' + element.title + '</option>');
                    dualllistbox.bootstrapDualListbox('refresh', true);
                } else {
                    $.each(editExtras, function (index, extra) {
                        if (element.id === extra.id) {
                            dualllistbox.append('<option value="' + element.id + '" selected="selected">' + element.title + '</option>');
                            dualllistbox.bootstrapDualListbox('refresh', true);
                            found = true;
                        }
                    })
                    if (found === false) {
                        dualllistbox.append('<option value="' + element.id + '">' + element.title + '</option>');
                        dualllistbox.bootstrapDualListbox('refresh', true);
                    }
                }

            })
            $('#selectpickerMains').selectpicker('refresh');
            $('#selectpickerServings').selectpicker('refresh');

        },
        error: function (xhr, textStatus, errorThrown) {
            alert('request failed');
        }
    });
}

function initMains() {
    var theUrl = server + "/rest/web/getMains";
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: theUrl,
        timeout: 5000,
        success: function (data, textStatus) {
            //           alert('request successful');
            $.each(data, function (index, element) {
                $('#selectpickerMains').append('<option>' + element.title + '</option>');

                mains.push({
                    id: element.id,
                    title: element.title
                });
            })
            if ($("#saveMeal").text() == "Update") {
                $('#selectpickerMains').selectpicker('val', editMeal.main.title);
                selectedIndex = 1;
            }


        },
        error: function (xhr, textStatus, errorThrown) {
            alert('request failed');
        }
    });

}

function initServings() {
    var theUrl = server + "/rest/data/getServings";
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: theUrl,
        timeout: 5000,
        success: function (data, textStatus) {
            //           alert('request successful');
            $.each(data, function (index, element) {
                $('#selectpickerServings').append('<option>' + element.title + '</option>');

                servings.push({
                    id: element.id,
                    title: element.title
                });
            })
            if ($("#saveMeal").text() == "Update") {
                $('#selectpickerServings').selectpicker('val', editMeal.serving.title);
                selectedServingIndex = 1;
            }
            


        },
        error: function (xhr, textStatus, errorThrown) {
            alert('request failed');
        }
        
        
    });

}

function addExtra(title, price) {
    var urlAddress = server + "/rest/web/addExtra";
    var extra = {
        id: 0,
        title: title,
        price: price
    };

    $.ajax({
        type: "POST",
        url: urlAddress,
        data: JSON.stringify(extra),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log("in add extra :" + data.id);
            dualllistbox.append('<option value="' + data.id + '"selected="selected">' + data.title + '</option>');
            dualllistbox.bootstrapDualListbox('refresh', true);

            extras.push({
                id: data.id,
                title: data.title,
                price: data.price
            });



        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });

}

function findExtra(extraId) {
    var found;
    $.each(extras, function (index, extra) {
        if (Number(extra.id) === Number(extraId)) {
            found = extra;
            return false; //break from loop
        }
    })
    return found;
}

function addNewMainToDB(mainTitle, callback) {
    var urlAddress = server + "/rest/web/addMain";
    var main = {
        id: 0,
        title: mainTitle
    };

    $.ajax({
        type: "POST",
        url: urlAddress,
        data: JSON.stringify(main),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            main = {
                id: data.id,
                title: data.title
            };
            callback(main);
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });
}

function addNewServingToDB(servingTitle, callback) {
    var urlAddress = server + "/rest/web/addServing";
    var serving = {
        id: 0,
        title: servingTitle
    };

    $.ajax({
        type: "POST",
        url: urlAddress,
        data: JSON.stringify(serving),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            main = {
                id: data.id,
                title: data.title
            };
            callback(main);
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });
}

